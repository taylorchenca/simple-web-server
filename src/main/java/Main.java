import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {
    static final String DOCUMENT_ROOT = "document_root";
    static final String PORT = "port";
    public static String documentRoot;

    public static void main(String[] args) throws IOException {
        CommandLine cmd = getCLIArguments(args);

        int port = Integer.parseInt(cmd.getOptionValue(PORT));
        documentRoot = cmd.getOptionValue(DOCUMENT_ROOT);

        SimpleListener simpleListener = new SimpleListener(port);
        simpleListener.listen();

    }

    private static CommandLine getCLIArguments(String[] args) {
        Options options = new Options();
        Option documentRoot = new Option("d", DOCUMENT_ROOT, true, "document root path");
        documentRoot.setType(String.class);
        Option port = new Option("p", PORT, true, "port");
        port.setType(Integer.TYPE);
        documentRoot.setRequired(true);
        port.setRequired(true);

        options.addOption(documentRoot);
        options.addOption(port);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return cmd;
    }
}
