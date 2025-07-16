package com.targetcar.agendador_tarefas.controller;

import com.targetcar.agendador_tarefas.business.TarefaService;
import com.targetcar.agendador_tarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(
            @RequestHeader("Authorization") String token,
            @RequestBody TarefasDTO dto) {
        return ResponseEntity.ok(tarefaService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscarListaDeTarefasporPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime dataFinal) {
        return ResponseEntity.ok(tarefaService.buscartarefasAgendadosPorPeriodo(dataInicial, dataFinal));
    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscarTarefasPorEmail(@RequestHeader(
            "Authorization") String token) {
        return ResponseEntity.ok(tarefaService.buscarTarefasPorEmail(token));
    }

}
