package com.mycompany.mutuales;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;

public class Impresora {

    private PrinterJob printerJob;

    public Impresora(String nombre) {
    }

    public Impresora() {
        this.printerJob = PrinterJob.getPrinterJob();
    }

    public void crearTrabajoImpresio(PrinterJob printerJob, Txt txt) throws PrinterException {
        this.printerJob = printerJob;
        establecerImpresora();
        prepararImpresion(txt);
        if (printerJob.printDialog()) {
            imprimirDocumento();
        }
    }

    public void imprimirArchivo(Txt tex) {
        try {
            establecerImpresora();
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.PRINT)) {
                File fileToPrint = new File(tex.getNombreArchivo());

                desktop.print(fileToPrint);
            } else {
                System.out.println("La acción de impresión no es compatible en este entorno.");
            }
        } catch (IOException e) {
        } catch (PrinterException ex) {
            Logger.getLogger(Impresora.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void establecerImpresora() throws PrinterException {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        if (printServices.length > 0) {
            PrintService selectedService = (PrintService) JOptionPane.showInputDialog(
                    null,
                    "Seleccione una impresora:",
                    "Seleccionar Impresora",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    printServices,
                    printServices[0]
            );

            if (selectedService != null) {
                    printerJob.setPrintService(selectedService);
            }
        } else {
            Utilidades.mensajeError("No se encontraron impresoras disponibles.");
        }
    }

    private void prepararImpresion(Txt txt) {
        int totalLinesInFile = getTotalLines(txt);
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                Font newFont = new Font("Serif", Font.PLAIN, 9);
                g2d.setFont(newFont);
                try {
                    dibujarTexto(g2d, txt, pageIndex);
                } catch (IOException ex) {
                    Logger.getLogger(Impresora.class.getName()).log(Level.SEVERE, null, ex);
                }
                int linesPerPage = 65; // Asegúrate de que este valor sea el mismo que en el método dibujarTexto
                if (pageIndex * linesPerPage >= totalLinesInFile) {
                    return Printable.NO_SUCH_PAGE;
                } else {
                    return Printable.PAGE_EXISTS;
                }
            }
        });
    }

    private void dibujarTexto(Graphics2D g2d, Txt txt, int pageIndex) throws FileNotFoundException, IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(txt.getNombreArchivo()))) {
            String line = reader.readLine();
            int y = 0;
            int linesPerPage = 65; // Ajusta este valor según el número de líneas que quieres por página
            int lineCount = 0;
            while (line != null) {
                if (lineCount >= pageIndex * linesPerPage && lineCount < (pageIndex + 1) * linesPerPage) {
                    y += g2d.getFontMetrics().getHeight();
                    g2d.drawString(line, 0, y);
                }
                line = reader.readLine();
                lineCount++;
            }
        }
    }

    private void imprimirDocumento() throws PrinterException {
        printerJob.print();
    }

    private int getTotalLines(Txt txt) {
        int totalLines = 0;
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(txt.getNombreArchivo()))) {
                while (reader.readLine() != null) {
                    totalLines++;
                }
            }
        } catch (IOException e) {
        }
        return totalLines;
    }

}
