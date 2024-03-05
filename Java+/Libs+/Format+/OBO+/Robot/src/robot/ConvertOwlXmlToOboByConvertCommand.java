package robot;

import org.obolibrary.robot.CommandLineInterface;

class ConvertOwlXmlToOboByConvertCommand {
    public static void main(String[] args) {
        String[] args2 = {
                "convert",
                "--check", "false",
                "--format", "OBO",
                "--input", "/home/aleks/Downloads/owl_ontologies/go-plus.owl",
                "--output", "/tmp/go-plus-4.obo"};
        CommandLineInterface.main(args2);
    }
}
