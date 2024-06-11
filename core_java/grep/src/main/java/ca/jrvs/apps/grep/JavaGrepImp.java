package ca.jrvs.apps.grep;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  private String regex;
  private String rootPath;
  private String outFile;

  @Override
  public void process() throws IOException {
    List<String> matchedLines = new ArrayList<>();

    for (final File file : listFiles(rootPath)) {
      for (final String line : readLines(file)) {
        if (containsPattern(line)) {
          matchedLines.add(line);
        }
      }
    }
    writeToFile(matchedLines);
  }

  @Override
  public List<File> listFiles(String rootDir) {
    List<File> returnList = new ArrayList<>();
    File root = new File(rootDir);
    File[] fileArr = root.listFiles();

    if (fileArr != null) {
      for (final File file : fileArr) {
        if (file.isDirectory()) {
          returnList.addAll(listFiles(file.toString()));
        } else {
          returnList.add(file);
        }
      }
    }

    return returnList;
  }

  @Override
  public List<String> readLines(File inputFile) {
    if (!inputFile.isFile()) {
      throw new IllegalArgumentException("Provided inputFile argument is not a file: " + inputFile);
    }
    List<String> returnList = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      String line = reader.readLine();

      while (line != null) {
        returnList.add(line);
        line = reader.readLine();
      }
    } catch (IOException e) {
      logger.error("Encountered error while reading file.", e);
      throw new RuntimeException(e);
    }

    return returnList;
  }

  @Override
  public boolean containsPattern(String line) {
    return line.matches(regex);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));

    for (final String line : lines) {
      writer.write(line);
      writer.newLine();
    }

    writer.close();
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  public static void main(String[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //Use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch (Exception ex){
      javaGrepImp.logger.error("Error: Unable to process", ex);
    }
  }
}
