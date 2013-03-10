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
import hudson.plugins.bazaar.BazaarTagAction;

import java.util.List;
import java.util.logging.Logger;

/**
 */
public class UtilBazaar {

    static public String getRevision( final AbstractScmTagAction scmTagAction )
    {
        String result = "";

        final BazaarTagAction bazaarTagAction = (BazaarTagAction)scmTagAction;
        if ( bazaarTagAction.hasRevisions() )
        {
            final List<BazaarTagAction.BazaarRevision> revs = bazaarTagAction.getRevisions();
            for ( final BazaarTagAction.BazaarRevision bazaarRevision : revs )
            {
                final String rev = bazaarRevision.getRevNo();
                LOGGER.info( "   rev=" + rev );
                if ( result.compareTo(rev) < 0 )
                {
                    result = rev;
                }
            }
        }

        return result;
    }

    private static final Logger LOGGER = Logger.getLogger(UtilSubversion.class.getName());

}

