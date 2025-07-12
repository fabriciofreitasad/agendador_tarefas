package com.targetcar.agendador_tarefas.controller;

import com.targetcar.agendador_tarefas.business.TarefaService;
import com.targetcar.agendador_tarefas.business.dto.TarefasDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefas(
            @RequestHeader("Authorization") String token,
            @RequestBody TarefasDTO dto            ) {
        return ResponseEntity.ok(tarefaService.gravarTarefa(token, dto));
    }
}
