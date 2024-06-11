package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepLambdaImp extends JavaGrepImp implements JavaGrepLambda {

  final Logger logger = LoggerFactory.getLogger(JavaGrepLambdaImp.class);

  @Override
  public Stream<File> listFilesStream(String rootDir) {
    try (Stream<Path> fileStream = Files.walk(Paths.get(rootDir))) {
      return fileStream.filter(file -> !Files.isDirectory(file))
          .map(Path::toFile);
    } catch (IOException e) {
      logger.error("Encountered error while listing files", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public Stream<String> readLinesStream(File inputFile) {
    if (!inputFile.isFile()) {
      throw new IllegalArgumentException("Provided inputFile argument is not a file: " + inputFile);
    }

    Stream<String> stream;
    try {
      stream = Files.lines(inputFile.toPath());
    } catch (IOException e) {
      logger.error("Encountered error while reading file.", e);
      throw new RuntimeException(e);
    }
    return stream;
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
    javaGrepLambdaImp.setRegex(args[0]);
    javaGrepLambdaImp.setRootPath(args[1]);
    javaGrepLambdaImp.setOutFile(args[2]);

    try {
      javaGrepLambdaImp.process();
    } catch (Exception ex){
      javaGrepLambdaImp.logger.error("Error: Unable to process", ex);
    }
  }
}
