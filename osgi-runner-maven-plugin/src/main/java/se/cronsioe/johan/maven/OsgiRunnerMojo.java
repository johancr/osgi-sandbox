package se.cronsioe.johan.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.*;

@Mojo(name = "run", defaultPhase = LifecyclePhase.INTEGRATION_TEST, requiresProject = true,
        inheritByDefault = false, requiresDependencyResolution = ResolutionScope.TEST)
public class OsgiRunnerMojo extends AbstractMojo {

    private final Collection<String> excludedGroupIds = new ArrayList<String>() {{
        add("org.apache.maven");
        add("org.apache.maven.plugin-tools");
        add("org.sonatype.aether");
        add("org.sonatype.sisu");
        add("org.sonatype.plexus");
        add("org.codehaus.plexus");
        add("org.easymock");
    }};

    private final Collection<String> excludedArtifactIds = new ArrayList<String>() {{
        add("osgi-runner-maven-plugin");
    }};

    private Socket socket;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(required = false)
    private Collection<String> artifacts;

    @Parameter(required = false)
    private Collection<String> groups;

    @Parameter(defaultValue = "", readonly = false, required = false)
    private String command;

    @Parameter(property = "osgi-runner.shell", defaultValue = "false")
    private boolean startShell;

    public void execute() throws MojoExecutionException, MojoFailureException {

        Framework framework = createFramework();
        try
        {
            framework.start();

            Collection<File> bundles = getRuntimeDependencies();
            install(bundles, framework);
            startBundles(framework);

            executeCommand();

            if (startShell) {
                framework.waitForStop(0);
            }
            else {
                executeCommand("stop 0");
                framework.waitForStop(10);
            }
        }
        catch (Exception ex)
        {
            throw new MojoExecutionException("Could not start OSGi container: " + ex, ex);
        }
    }

    private Framework createFramework() {
        FrameworkFactory frameworkFactory = ServiceLoader.load(
                FrameworkFactory.class).iterator().next();

        Map<String, String> config = new HashMap<String, String>();
        config.put("org.osgi.framework.storage.clean", "onFirstInit");
        config.put("felix.cache.rootdir", project.getBuild().getTestOutputDirectory());

        return frameworkFactory.newFramework(config);
    }

    private Collection<File> getRuntimeDependencies() {
        Collection<File> bundles = new ArrayList<File>();
        bundles.add(getProjectArtifact());

        for (Artifact artifact : project.getArtifacts())
        {
            String artifactId = artifact.getArtifactId();
            String groupId = artifact.getGroupId();

            if (isFrameworkBundle(artifactId) || isExcludedGroupId(groupId) || isExcludedArtifactId(artifactId))
            {
                continue;
            }

            bundles.add(artifact.getFile());
        }

        return bundles;
    }

    private void install(Collection<File> bundles, Framework framework) throws BundleException, MalformedURLException {
        for (File bundle : bundles)
        {
            framework.getBundleContext().installBundle(bundle.toURI().toURL().toExternalForm());
        }
    }

    private void startBundles(Framework framework) throws BundleException {
        for (Bundle bundle : framework.getBundleContext().getBundles())
        {
            if (bundle.getHeaders().get("Fragment-Host") == null)
            {
                bundle.start();
            }
        }
    }

    private void executeCommand() {

        if (command == null)
        {
            return;
        }

        executeCommand(command);
    }

    private void executeCommand(String command) {
        try
        {
            if (socket == null) {
                socket = new Socket("localhost", 6666);
            }
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(command);
            writer.flush();
        }
        catch (IOException ex)
        {
            throw new RuntimeException("Could not execute command: " + command, ex);
        }
    }

    private File getProjectArtifact() {
        return project.getArtifact().getFile();
    }

    private boolean isFrameworkBundle(String artifactId) {
        return artifactId.equals("org.apache.felix.main")
                || artifactId.equals("org.apache.felix.framework");
    }

    private boolean isExcludedGroupId(String groupId) {
        return (groups != null && groups.contains(groupId))
                || excludedGroupIds.contains(groupId);
    }

    private boolean isExcludedArtifactId(String artifactId) {
        return (artifacts != null && artifacts.contains(artifactId))
                || excludedArtifactIds.contains(artifactId);
    }
}