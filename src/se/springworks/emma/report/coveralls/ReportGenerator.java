package se.springworks.emma.report.coveralls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import se.springworks.coveralls.CoverallsReport;
import se.springworks.coveralls.CoverallsReport.SourceFile;
import se.springworks.coveralls.SourceLoader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vladium.emma.EMMARuntimeException;
import com.vladium.emma.IAppErrorCodes;
import com.vladium.emma.data.ICoverageData;
import com.vladium.emma.data.IMetaData;
import com.vladium.emma.report.AbstractReportGenerator;
import com.vladium.emma.report.AllItem;
import com.vladium.emma.report.ClassItem;
import com.vladium.emma.report.IItem;
import com.vladium.emma.report.ItemComparator;
import com.vladium.emma.report.PackageItem;
import com.vladium.emma.report.SourcePathCache;
import com.vladium.emma.report.SrcFileItem;
import com.vladium.util.IProperties;
import com.vladium.util.IntObjectMap;
import com.vladium.util.Property;
import com.vladium.util.asserts.$assert;

public class ReportGenerator extends AbstractReportGenerator
implements IAppErrorCodes {

	private static final String TYPE = "coveralls";

	private LinkedList<IItem> m_queue;

	private CoverallsReport report;
	
	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void process(IMetaData mdata, ICoverageData cdata,
			SourcePathCache cache, IProperties properties)
					throws EMMARuntimeException {

		initialize(mdata, cdata, cache, properties);
		
		report = new CoverallsReport();

		File outDir = m_settings.getOutDir();
		if ((outDir == null) /* this should never happen */
				|| (outDir.equals(new File(Property.getSystemProperty(
						"user.dir", ""))))) {
			outDir = new File("coverage");
			m_settings.setOutDir(outDir);
		}

        File outFile = m_settings.getOutFile ();
        if (outFile == null)
        {
            outFile = new File ("coverage.json");
            m_settings.setOutFile (outFile);
        }
		
		m_queue = new LinkedList<IItem>();

		for (m_queue.add(m_view.getRoot()); !m_queue.isEmpty();) {
			final IItem head = m_queue.removeFirst();
			m_log.info("queue " +  head.getName() );
			head.accept(this, null);
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			mapper.writeValue(outFile, report);
		} catch (JsonProcessingException e) {
			throw new EMMARuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m_log.info("coveralls report done");
	}

	@Override
	public void cleanup() {
		m_queue = null;

		super.cleanup();
	}
	
    @Override
	public Object visit (final AllItem item, final Object ctx)
    {
    	m_log.info("visit() all");
        final ItemComparator order = m_typeSortComparators [PackageItem.getTypeMetadata ().getTypeID ()];
        for (Iterator<IItem> packages = item.getChildren(order); packages.hasNext();)
        {
            final IItem pkg = packages.next ();
            m_queue.addLast (pkg);
        }
    	
    	return ctx;
    }

    @Override
	public Object visit (final PackageItem item, final Object ctx)
    {
    	m_log.info("visit() " + item.getName());
        final ItemComparator order = m_typeSortComparators [m_srcView ? SrcFileItem.getTypeMetadata ().getTypeID () : ClassItem.getTypeMetadata ().getTypeID ()];                
        for (Iterator<IItem> srcORclsFiles = item.getChildren (order); srcORclsFiles.hasNext ();)
        {
            final IItem srcORcls = srcORclsFiles.next ();
            m_queue.addLast(srcORcls);
        }
    	return ctx;
    }
    
	@Override
	public Object visit(final SrcFileItem item, final Object ctx) {
		m_log.info("visit()");
		
		final String packageVMName = ((PackageItem) item.getParent()).getVMName();
		final String fileName = item.getName();
		final File srcFile = m_cache.find(packageVMName, fileName);		
		if(srcFile == null || !srcFile.exists()) {
			return ctx;
		}
		
		SourceFile sourceFile = new SourceFile(fileName, SourceLoader.load(srcFile));
		report.addSourceFile(sourceFile);
		
		
		IntObjectMap lineCoverageMap = item.getLineCoverage();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(srcFile), 4096);
			int l = 1;
			for (String line; (line = in.readLine()) != null; ++l) {
				final SrcFileItem.LineCoverageData lCoverageData = (SrcFileItem.LineCoverageData) lineCoverageMap.get(l);
				if (lCoverageData != null) {
					switch (lCoverageData.m_coverageStatus) {
					case SrcFileItem.LineCoverageData.LINE_COVERAGE_ZERO:
						sourceFile.setNoCoverage(l);
						break;

					case SrcFileItem.LineCoverageData.LINE_COVERAGE_PARTIAL:
						sourceFile.addCoverage(l);
						break;

					case SrcFileItem.LineCoverageData.LINE_COVERAGE_COMPLETE:
						sourceFile.addCoverage(l);
						sourceFile.addCoverage(l);
						break;

					default:
						$assert.ASSERT(false, "invalid line coverage status: "
								+ lCoverageData.m_coverageStatus);

					} // end of switch
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Throwable ignore) {
				}
			in = null;
		}

		return ctx;
	}
}
