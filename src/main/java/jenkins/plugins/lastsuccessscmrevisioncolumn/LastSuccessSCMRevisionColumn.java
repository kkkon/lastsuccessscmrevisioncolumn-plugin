/**
 * The MIT License
 * 
 * Copyright (C) 2012 KK.Kon
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
package jenkins.plugins.lastsuccessscmrevisioncolumn;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.model.Run;
import hudson.scm.AbstractScmTagAction;
import hudson.views.ListViewColumn;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.List;
import java.util.logging.Logger;
import jenkins.plugins.lastsuccessscmrevisioncolumn.scm.UtilBazaar;
import jenkins.plugins.lastsuccessscmrevisioncolumn.scm.UtilSubversion;

/**
 */
public class LastSuccessSCMRevisionColumn extends ListViewColumn {

    public boolean isSubversion( final AbstractScmTagAction scmTagAction )
    {
        if ( null == scmTagAction )
        {
            return false;
        }

        Class<?> clazz = null;
        try
        {
            clazz = Class.forName("hudson.scm.SubversionTagAction");
        }
        catch (ClassNotFoundException e)
        {
            
        }

        if ( null != clazz )
        {
            if ( clazz.isInstance(scmTagAction) )
            {
                return true;
            }
        }

        return false;
    }

    public boolean isBazaar( final AbstractScmTagAction scmTagAction )
    {
        if ( null == scmTagAction )
        {
            return false;
        }

        Class<?> clazz = null;
        try
        {
            clazz = Class.forName("hudson.plugins.bazaar.BazaarTagAction");
        }
        catch (ClassNotFoundException e)
        {
            
        }

        if ( null != clazz )
        {
            if ( clazz.isInstance(scmTagAction) )
            {
                return true;
            }
        }

        return false;
    }

    @DataBoundConstructor
    public LastSuccessSCMRevisionColumn() {
    }

    public String getRevision(Job job)
    {
        boolean haveResult = false;
        String result = "";

        LOGGER.info("jobName:" + job.getName() );
        final Run run = job.getLastSuccessfulBuild();

        LOGGER.info( "jobProperties:" + job.getProperties().toString() );
        LOGGER.info( "jobActions:" + job.getActions().toString() );
        if ( null != run )
        {
            LOGGER.info( "runActions:" + run.getActions().toString() );
            LOGGER.info( "build no: " + run.getNumber() );

            LOGGER.info( "runActions:" + run.getActions().toString() );
            List<Action> actions = run.getActions();
            for ( final Action action : actions )
            {
                if ( action instanceof AbstractScmTagAction )
                {
                    AbstractScmTagAction scmTagAction = (AbstractScmTagAction)action;
                    LOGGER.info( "  scmTagAction: " + scmTagAction.getUrlName() );
                    if ( isSubversion(scmTagAction) )
                    {
                        final long rev = UtilSubversion.getRevision( scmTagAction );
                        {
                            LOGGER.info( "   rev=" + rev );
                            final String revStr = String.valueOf( rev );
                            if ( result.compareTo(revStr) < 0 )
                            {
                                haveResult = true;
                                result = revStr;
                            }
                        }
                    }
                    else
                    if ( isBazaar(scmTagAction) )
                    {
                        final String rev = UtilBazaar.getRevision( scmTagAction );
                        if ( result.compareTo(rev) < 0 )
                        {
                            haveResult = true;
                            result = rev;
                        }
                    }
                }

//                if ( action instanceof SCMRevisionState )
//                {
//                    // SVNRevisionState doesn't have public-method...
//                    SCMRevisionState scmRevState = (SCMRevisionState)action;
//                    LOGGER.info( " scmRevState#DisplayName:" + scmRevState.getDisplayName() );
//
//                    final String className = action.getClass().getName();
//                    LOGGER.info( " className:" + className );
//                    if ( 0 == "hudson.scm.SVNRevisionState".compareTo(className) )
//                    {
//                        LOGGER.info( " SVN" );
//                    }
//                }
            }
        }

        if ( haveResult )
        {
            return result;
        }

        return null;
    }

    // Overridden for better type safety.
    // If your plugin doesn't really define any property on Descriptor,
    // you don't have to do this.
    @Override
    public DescriptorImpl getDescriptor() {
        return (DescriptorImpl)super.getDescriptor();
    }

    /**
     * Descriptor for {@link LastSuccessSCMRevisionColumn}. Used as a singleton.
     * The class is marked as public so that it can be accessed from views.
     *
     */
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends Descriptor<ListViewColumn> {

        /**
         * This human readable name is used in the configuration screen.
         */
        public String getDisplayName() {
            return "Last Success SCMRevision Column";
        }

    }

    private static final Logger LOGGER = Logger.getLogger(LastSuccessSCMRevisionColumn.class.getName());

}

