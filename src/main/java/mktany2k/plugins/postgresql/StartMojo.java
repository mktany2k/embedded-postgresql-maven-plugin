package mktany2k.plugins.postgresql;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import java.io.IOException;

@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartMojo extends AbstractMojo {

    @Parameter
    private Database database;

    public void execute() throws MojoExecutionException, MojoFailureException {
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime = PostgresStarter.getDefaultInstance();

        try {
            PostgresConfig config =
                    new PostgresConfig(
                            Version.V9_5_0,
                            new AbstractPostgresConfig.Net("localhost", database.getPort()),
                            new AbstractPostgresConfig.Storage(database.getName()),
                            new AbstractPostgresConfig.Timeout(),
                            new AbstractPostgresConfig.Credentials("binlist_owner", "ias1234")
                    );

            getLog().info(String.format("Configuration: %s%n%s", config.net().host(), config.net().port()));
            PostgresExecutable exec = runtime.prepare(config);
            ShareObject.process = exec.start();
            getLog().info("Database started");
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to start embedded Postgresql", e);
        }
    }
}
