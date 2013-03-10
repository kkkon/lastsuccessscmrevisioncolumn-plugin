/**
 * The MIT License
 * 
 * Copyright (C) 2012-2013 Kiyofumi Kondoh
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jenkins.plugins.lastsuccessscmrevisioncolumn.scm;

import hudson.scm.AbstractScmTagAction;
import hudson.scm.SubversionSCM;
import hudson.scm.SubversionTagAction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 */
public class UtilSubversion {

    static public long getRevision( final AbstractScmTagAction scmTagAction )
    {
        long result = -1;

        final SubversionTagAction svnTagAction = (SubversionTagAction)scmTagAction;
        final Map<SubversionSCM.SvnInfo,List<String>> tags = svnTagAction.getTags();
        final Set<SubversionSCM.SvnInfo> keys = tags.keySet();
        for ( final SubversionSCM.SvnInfo svnInfo : keys )
        {
            LOGGER.info( "   rev=" + svnInfo.revision );
            if ( result < svnInfo.revision )
            {
                result = svnInfo.revision;
            }
        }

        return result;
    }

    private static final Logger LOGGER = Logger.getLogger(UtilSubversion.class.getName());

}

