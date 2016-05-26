package mktany2k.plugins.postgresql;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "stop")
public class StopMojo extends AbstractMojo {
    public void execute() throws MojoExecutionException, MojoFailureException {
//        getLog().info("In Stop. Received " + ShareObject.now);
        ShareObject.process.stop();
//        ShareObject.anotherProcess.stop();
        getLog().info("Database Stopped");
    }
}
