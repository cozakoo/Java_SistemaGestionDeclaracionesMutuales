package GUI;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author dgc06
 */
public class Filter extends FileFilter{
  
        @Override
        public boolean accept(File file) {
            return file.isDirectory() || file.getName().toLowerCase().endsWith(".txt");
        }

        @Override
        public String getDescription() {
            return "Archivos de texto (.txt)";
        }
}
