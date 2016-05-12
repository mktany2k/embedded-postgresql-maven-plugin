package mktany2k.plugins.postgresql;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;

import java.io.IOException;
import java.time.LocalDateTime;

@Mojo(name = "start")
public class StartMojo extends AbstractMojo {
    public void execute() throws MojoExecutionException, MojoFailureException {
        ShareObject.now = LocalDateTime.now();
        getLog().info("started at " + ShareObject.now);
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();

        try {
            PostgresConfig config = PostgresConfig.defaultWithDbName("test");
            PostgresExecutable exec = runtime.prepare(config);
            ShareObject.process = exec.start();
            getLog().info("Database started");
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to start embedded Postgresql", e);
        }
    }
}
