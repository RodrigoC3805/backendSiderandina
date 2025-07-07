package dsw.backendSiderandina.service;

import dsw.backendSiderandina.dto.AsistenciaRequest;
import dsw.backendSiderandina.dto.ReporteAsistenciaDTO;
import dsw.backendSiderandina.dto.ReporteHorasExtrasDTO;
import dsw.backendSiderandina.dto.ReportePuntualidadDTO;
import dsw.backendSiderandina.model.AsistenciaDiaria;
import dsw.backendSiderandina.model.Trabajador;
import dsw.backendSiderandina.repository.AsistenciaDiariaRepository;
import dsw.backendSiderandina.repository.TrabajadorRepository;
import jakarta.servlet.ServletOutputStream;

import java.time.Duration;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


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

    public List<ReporteAsistenciaDTO> obtenerReporteHorasTrabajadas(String fechaInicio, String fechaFin) {
        List<AsistenciaDiaria> asistencias = asistenciaRepo.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate inicio = (fechaInicio != null && !fechaInicio.isEmpty()) ? LocalDate.parse(fechaInicio, formatter) : null;
        LocalDate fin = (fechaFin != null && !fechaFin.isEmpty()) ? LocalDate.parse(fechaFin, formatter) : null;

        return asistencias.stream()
            .filter(a -> a.getHoraIngreso() != null && a.getHoraSalida() != null)
            .filter(a -> {
                if (inicio != null && fin != null) {
                    return !a.getFecha().isBefore(inicio) && !a.getFecha().isAfter(fin);
                } else if (inicio != null) {
                    return !a.getFecha().isBefore(inicio);
                } else if (fin != null) {
                    return !a.getFecha().isAfter(fin);
                }
                return true;
            })
            .map(a -> {
                double horas = Duration.between(a.getHoraIngreso(), a.getHoraSalida()).toMinutes() / 60.0;
                String nombre = a.getTrabajador().getNombres() + " " + a.getTrabajador().getApellidoPaterno() + " " + a.getTrabajador().getApellidoMaterno();
                return new ReporteAsistenciaDTO(nombre, a.getFecha().toString(), Math.round(horas * 100.0) / 100.0);
            })
            .collect(Collectors.toList());
    }

    public ReportePuntualidadDTO obtenerReportePuntualidad(String fechaInicio, String fechaFin) {
        List<AsistenciaDiaria> asistencias = asistenciaRepo.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = (fechaInicio != null && !fechaInicio.isEmpty()) ? LocalDate.parse(fechaInicio, formatter) : null;
        LocalDate fin = (fechaFin != null && !fechaFin.isEmpty()) ? LocalDate.parse(fechaFin, formatter) : null;

        // Hora estándar de ingreso
        LocalTime horaEstandar = LocalTime.of(8, 0);

        long puntuales = 0, tardanzas = 0, faltas = 0;

        // Filtrar por fechas si corresponde
        List<AsistenciaDiaria> filtradas = asistencias.stream()
            .filter(a -> {
                if (inicio != null && fin != null) {
                    return !a.getFecha().isBefore(inicio) && !a.getFecha().isAfter(fin);
                } else if (inicio != null) {
                    return !a.getFecha().isBefore(inicio);
                } else if (fin != null) {
                    return !a.getFecha().isAfter(fin);
                }
                return true;
            })
            .collect(Collectors.toList());
    
        for (AsistenciaDiaria a : filtradas) {
            if (a.getHoraIngreso() == null) {
                faltas++;
            } else if (!a.getHoraIngreso().isAfter(horaEstandar)) {
                puntuales++;
            } else {
                tardanzas++;
            }
        }
        return new ReportePuntualidadDTO(puntuales, tardanzas, faltas);
    }

    public List<ReporteHorasExtrasDTO> obtenerReporteHorasExtras(String fechaInicio, String fechaFin) {
        List<AsistenciaDiaria> asistencias = asistenciaRepo.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate inicio = (fechaInicio != null && !fechaInicio.isEmpty()) ? LocalDate.parse(fechaInicio, formatter) : null;
        LocalDate fin = (fechaFin != null && !fechaFin.isEmpty()) ? LocalDate.parse(fechaFin, formatter) : null;

        // Jornada estándar: 9 horas
        double jornada = 9.0;

        Map<String, Double> horasExtrasPorTrabajador = new HashMap<>();

        asistencias.stream()
            .filter(a -> a.getHoraIngreso() != null && a.getHoraSalida() != null)
            .filter(a -> {
                if (inicio != null && fin != null) {
                    return !a.getFecha().isBefore(inicio) && !a.getFecha().isAfter(fin);
                } else if (inicio != null) {
                    return !a.getFecha().isBefore(inicio);
                } else if (fin != null) {
                    return !a.getFecha().isAfter(fin);
                }
                return true;
            })
            .forEach(a -> {
                double horas = Duration.between(a.getHoraIngreso(), a.getHoraSalida()).toMinutes() / 60.0;
                double extras = horas > jornada ? horas - jornada : 0.0;
                if (extras > 0) {
                    String nombre = a.getTrabajador().getNombres() + " " + a.getTrabajador().getApellidoPaterno() + " " + a.getTrabajador().getApellidoMaterno();
                    horasExtrasPorTrabajador.put(nombre, horasExtrasPorTrabajador.getOrDefault(nombre, 0.0) + extras);
                }
            });
        return horasExtrasPorTrabajador.entrySet().stream()
            .map(e -> new ReporteHorasExtrasDTO(e.getKey(), Math.round(e.getValue() * 100.0) / 100.0))
            .collect(Collectors.toList());
    }
   
}