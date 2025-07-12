package com.targetcar.agendador_tarefas.business;

import com.targetcar.agendador_tarefas.business.dto.TarefasDTO;
import com.targetcar.agendador_tarefas.business.mapper.TarefaConverter;
import com.targetcar.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.targetcar.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.targetcar.agendador_tarefas.infrastructure.repository.TarefasRepository;
import com.targetcar.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasRepository tarefaRepository;
    private final TarefaConverter tarefaConverter;
    private final  JwtUtil jwtUtil;

    public TarefasDTO gravarTarefa(String token,TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);
        return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
    }
}
