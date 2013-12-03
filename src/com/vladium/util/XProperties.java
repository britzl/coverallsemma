/* Copyright (C) 2003 Vladimir Roubtsov. All rights reserved.
 * 
 * This program and the accompanying materials are made available under
 * the terms of the Common Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/cpl-v10.html
 * 
 * $Id: XProperties.java,v 1.1.1.1 2004/05/09 16:57:56 vlad_r Exp $
 */
package com.vladium.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

// ----------------------------------------------------------------------------
/**
 * @author Vlad Roubtsov, (C) 2003
 */
public
class XProperties extends Properties
{
    // public: ................................................................
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public XProperties ()
    {
    }
    
    public XProperties (final Properties base)
    {
        super (base);
    }
    
    @Override
	public void list (final PrintStream out)
    {
        final Set<String> _propertyNames = new TreeSet<String> ();
        
        // note: must use propertyNames() because that is the only method that recurses
        for (Enumeration<?> propertyNames = propertyNames (); propertyNames.hasMoreElements (); )
        {
            _propertyNames.add ((String)propertyNames.nextElement ());
        }
        
        for (Iterator<String> i = _propertyNames.iterator (); i.hasNext (); )
        {
            final String n = i.next ();
            final String v = getProperty (n);
            
            out.println (n + ":\t[" + v + "]");
        }
    }
    
    @Override
	public void list (final PrintWriter out)
    {
        final Set<String> _propertyNames = new TreeSet<String> ();
        
        // note: must use propertyNames() because that is the only method that recurses
        for (Enumeration<?> propertyNames = propertyNames (); propertyNames.hasMoreElements (); )
        {
            _propertyNames.add ((String)propertyNames.nextElement ());
        }
        
        for (Iterator<String> i = _propertyNames.iterator (); i.hasNext (); )
        {
            final String n = i.next ();
            final String v = getProperty (n);
            
            out.println (n + ":\t[" + v + "]");
        }
    }
    
    // protected: .............................................................
    
    // package: ...............................................................
    
    // private: ...............................................................

} // end of class
// ----------------------------------------------------------------------------