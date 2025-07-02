package dsw.backendSiderandina.controller;

import dsw.backendSiderandina.dto.JustificacionAsistenciaRequest;
import dsw.backendSiderandina.service.JustificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/rrhh/justificacion")
public class JustificacionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JustificacionService justificacionService;

    @PostMapping(path = "/create")
    public ResponseEntity<?> createJustificacion(@ModelAttribute JustificacionAsistenciaRequest request, @RequestParam("doc_sustento")MultipartFile doc){
        try {
            return ResponseEntity.ok(justificacionService.createJustificacion(request, doc));
        } catch (Exception e){
            logger.error("Error inesperado");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
