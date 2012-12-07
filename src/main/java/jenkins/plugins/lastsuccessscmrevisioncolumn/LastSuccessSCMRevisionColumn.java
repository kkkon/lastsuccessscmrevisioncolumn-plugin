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

import hudson.Launcher;
import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.Action;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.model.Run;
import hudson.scm.ChangeLogSet;
import hudson.scm.SCMRevisionState;
import hudson.views.ListViewColumn;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.QueryParameter;

import java.util.List;
import java.util.logging.Logger;

/**
 */
public class LastSuccessSCMRevisionColumn extends ListViewColumn {

    @DataBoundConstructor
    public LastSuccessSCMRevisionColumn() {
    }
    
    public String getRevision(Job job)
    {
        LOGGER.info("jobName:" + job.getName() );
        final Run run = job.getLastSuccessfulBuild();

        LOGGER.info( "jobProperties:" + job.getProperties().toString() );
        LOGGER.info( "jobActions:" + job.getActions().toString() );
        if ( null != run )
        {
            LOGGER.info( "runActions:" + run.getActions().toString() );
            LOGGER.info( "build no: " + run.getNumber() );

//            if ( run instanceof AbstractBuild<?,?> )
//            {
//                final AbstractBuild<?,?> build = (AbstractBuild<?,?>)run;
//                final ChangeLogSet<? extends ChangeLogSet.Entry> changeSet = build.getChangeSet();
//                if ( null != changeSet )
//                {
//                    LOGGER.info( " changeSet:" + build.getChangeSet().toString() );
//                    for ( ChangeLogSet.Entry entry : changeSet )
//                    {
//                        LOGGER.info( "  entry: " + entry );
//                        LOGGER.info( "   commitId: " + entry.getCommitId() );
//                    }
//                }
//            }

//          // SVNRevisionState doesn't have public-method...
//            LOGGER.info( "runActions:" + run.getActions().toString() );
//            List<Action> actions = run.getActions();
//            for ( final Action action : actions )
//            {
//                if ( action instanceof SCMRevisionState )
//                {
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
//            }
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

