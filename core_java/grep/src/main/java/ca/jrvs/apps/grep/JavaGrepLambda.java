package ca.jrvs.apps.grep;

import java.io.File;
import java.util.stream.Stream;

public interface JavaGrepLambda extends JavaGrep {

  /**
   * Traverse a given directory and return all files
   * @param rootDir input directory
   * @return a stream of files under the rootDir
   */
  Stream<File> listFilesStream(String rootDir);

  /**
   * Read a file and return all the lines
   * @param inputFile file to be read
   * @return a stream of lines from the inputFile
   * @throws IllegalArgumentException if a given inputFile is not a file
   */
  Stream<String> readLinesStream(File inputFile);
}