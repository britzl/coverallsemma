package se.springworks.emma.report.coveralls;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import se.springworks.coveralls.CoverallsReport;
import se.springworks.coveralls.CoverallsReport.SourceFile;
import se.springworks.coveralls.SourceLoader;

import com.vladium.emma.EMMARuntimeException;
import com.vladium.emma.IAppErrorCodes;
import com.vladium.emma.data.ICoverageData;
import com.vladium.emma.data.IMetaData;
import com.vladium.emma.report.AbstractReportGenerator;
import com.vladium.emma.report.IItem;
import com.vladium.emma.report.PackageItem;
import com.vladium.emma.report.SourcePathCache;
import com.vladium.emma.report.SrcFileItem;
import com.vladium.util.IProperties;
import com.vladium.util.IntObjectMap;
import com.vladium.util.ObjectIntMap;
import com.vladium.util.Property;
import com.vladium.util.asserts.$assert;

public class ReportGenerator extends AbstractReportGenerator
implements IAppErrorCodes {

	private static final String TYPE = "coveralls";

	private LinkedList<IItem> m_queue;
	private IDGenerator m_reportIDNamespace;

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

		File outDir = m_settings.getOutDir();
		if ((outDir == null) /* this should never happen */
				|| (outDir.equals(new File(Property.getSystemProperty(
						"user.dir", ""))))) {
			outDir = new File("coverage");
			m_settings.setOutDir(outDir);
		}

		m_queue = new LinkedList<IItem>();
		m_reportIDNamespace = new IDGenerator(mdata.size());

		for (m_queue.add(m_view.getRoot()); !m_queue.isEmpty();) {
			final IItem head = (IItem) m_queue.removeFirst();

			head.accept(this, null);
		}

		m_reportIDNamespace = null;
	}

	@Override
	public void cleanup() {
		m_queue = null;
		m_reportIDNamespace = null;

		super.cleanup();
	}

	@Override
	public Object visit(final SrcFileItem item, final Object ctx) {
		final String packageVMName = ((PackageItem) item.getParent()).getVMName();
		final String fileName = item.getName();
		final File srcFile = m_cache.find(packageVMName, fileName);

		SourceFile sourceFile = new SourceFile(fileName, SourceLoader.load(srcFile));
		IntObjectMap lineCoverageMap = item.getLineCoverage();

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(srcFile), 4096);
			int l = 1;
			for (String line; (line = in.readLine()) != null; ++l) {
				final SrcFileItem.LineCoverageData lCoverageData = (SrcFileItem.LineCoverageData) lineCoverageMap
						.get(l);
				if (lCoverageData != null) {
					switch (lCoverageData.m_coverageStatus) {
					case SrcFileItem.LineCoverageData.LINE_COVERAGE_ZERO:
						break;

					case SrcFileItem.LineCoverageData.LINE_COVERAGE_PARTIAL:
						break;

					case SrcFileItem.LineCoverageData.LINE_COVERAGE_COMPLETE:
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

	private static final class IDGenerator {
		IDGenerator() {
			m_namespace = new ObjectIntMap(101);
			m_out = new int[1];
		}

		IDGenerator(final int initialCapacity) {
			m_namespace = new ObjectIntMap(initialCapacity);
			m_out = new int[1];
		}

		String getID(final String key) {
			final int[] out = m_out;
			final int ID;

			if (m_namespace.get(key, out))
				ID = out[0];
			else {
				ID = m_namespace.size();
				m_namespace.put(key, ID);
			}

			return Integer.toHexString(ID);
		}

		private final ObjectIntMap /* key:String->ID */m_namespace;
		private final int[] m_out;

	} // end of nested class

}
