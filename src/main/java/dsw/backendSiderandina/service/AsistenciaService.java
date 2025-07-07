package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.AsistenciaRequest;
import dsw.backendSiderandina.model.AsistenciaDiaria;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.repository.AsistenciaDiariaRepository;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import jakarta.servlet.ServletOutputStream;

import java.util.List;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;

@Service
public class AsistenciaService {
    @Autowired
    private AsistenciaDiariaRepository asistenciaRepo;
    @Autowired
    private TrabajadorRepository trabajadorRepo;

    // Registrar ingreso
    public AsistenciaDiaria registrarAsistenciaIngreso(AsistenciaRequest req) {
        Trabajador trabajador = trabajadorRepo.findByNumeroDocumento(req.getNumeroDocumento())
            .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        AsistenciaDiaria asistencia = AsistenciaDiaria.builder()
            .trabajador(trabajador)
            .fecha(req.getFecha())
            .horaIngreso(req.getHoraIngreso())
            .build();
        return asistenciaRepo.save(asistencia);
    }

    // Registrar salida
    public AsistenciaDiaria registrarAsistenciaSalida(AsistenciaRequest req) {
        // Busca la asistencia del día para ese trabajador y actualiza la hora de salida
        Trabajador trabajador = trabajadorRepo.findByNumeroDocumento(req.getNumeroDocumento())
            .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        AsistenciaDiaria asistencia = asistenciaRepo.findByTrabajadorAndFecha(trabajador, req.getFecha())
            .orElseThrow(() -> new RuntimeException("No se encontró registro de ingreso"));
        asistencia.setHoraSalida(req.getHoraSalida());
        return asistenciaRepo.save(asistencia);
    }

    public List<AsistenciaDiaria> listarAsistencias() {
        return asistenciaRepo.findAll();
    }

    public List<AsistenciaDiaria> listarAsistenciasPorDocumento(String numeroDocumento) {
        Trabajador trabajador = trabajadorRepo.findByNumeroDocumento(numeroDocumento)
            .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        return asistenciaRepo.findByTrabajador(trabajador);
    }

    public void exportarAsistenciasExcel(ServletOutputStream outputStream) throws IOException {
        List<AsistenciaDiaria> asistencias = asistenciaRepo.findAll();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Asistencias");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Nombre");
            header.createCell(1).setCellValue("N° Documento");
            header.createCell(2).setCellValue("Cargo");
            header.createCell(3).setCellValue("Fecha");
            header.createCell(4).setCellValue("Hora Entrada");
            header.createCell(5).setCellValue("Hora Salida");

            int rowIdx = 1;
            for (AsistenciaDiaria a : asistencias) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(a.getTrabajador().getNombres() + " " +
                                                      a.getTrabajador().getApellidoPaterno() + " " +
                                                      a.getTrabajador().getApellidoMaterno());
                row.createCell(1).setCellValue(a.getTrabajador().getNumeroDocumento());
                /*row.createCell(2).setCellValue(a.getTrabajador().getTipoTrabajador() != null
                ? a.getTrabajador().getTipoTrabajador().getDescripcion() : "-");
                */
                row.createCell(3).setCellValue(a.getFecha() != null ? a.getFecha().toString() : "");
                row.createCell(4).setCellValue(a.getHoraIngreso() != null ? a.getHoraIngreso().toString() : "");
                row.createCell(5).setCellValue(a.getHoraSalida() != null ? a.getHoraSalida().toString() : "");
            }
            workbook.write(outputStream);
        }
    }
   
}