package com.targetcar.agendador_tarefas.business;

import com.targetcar.agendador_tarefas.business.dto.TarefasDTO;
import com.targetcar.agendador_tarefas.business.mapper.TarefaConverter;
import com.targetcar.agendador_tarefas.business.mapper.TarefaUpdateConverter;
import com.targetcar.agendador_tarefas.infrastructure.entity.TarefasEntity;
import com.targetcar.agendador_tarefas.infrastructure.enums.StatusNotificacaoEnum;
import com.targetcar.agendador_tarefas.infrastructure.exceptions.ResourceNotFoudException;
import com.targetcar.agendador_tarefas.infrastructure.repository.TarefasRepository;
import com.targetcar.agendador_tarefas.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefasRepository tarefaRepository;
    private final TarefaConverter tarefaConverter;
    private final JwtUtil jwtUtil;
    private final TarefaUpdateConverter tarefaUpdateConverter;

    public TarefasDTO gravarTarefas(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusNotificacaoEnum(StatusNotificacaoEnum.PENDENTE);
        dto.setEmailUsuario(email);
        TarefasEntity entity = tarefaConverter.paraTarefaEntity(dto);
        return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
    }

    public List<TarefasDTO> buscartarefasAgendadosPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return tarefaConverter.paraListaTarefasDTO(
                tarefaRepository.findByDataEvantoBetween(dataInicial, dataFinal));
    }

    public List<TarefasDTO> buscarTarefasPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        return tarefaConverter.paraListaTarefasDTO(
                tarefaRepository.findByEmailUsuario(email));
    }

    public void deletaTarefaPorId(String id) {
        try {
            tarefaRepository.deleteById(id);
        } catch (ResourceNotFoudException e) {
            throw new ResourceNotFoudException("erro ao deletar tarefa por id, id enestxistente id" + id,
                    e.getCause());
        }
    }

    public TarefasDTO alterarStatus(StatusNotificacaoEnum status, String id) {
        try {
            TarefasEntity entity = tarefaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoudException("tarefa não encontrada" + id));
            entity.setStatusNotificacaoEnum(status);
            return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoudException e) {
            throw new ResourceNotFoudException("Erro ao alterar status da tarefa" + e.getCause());
        }
    }

    public TarefasDTO updatetarefas(TarefasDTO dto, String id) {
        try {
            TarefasEntity entity = tarefaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoudException("tarefa não encontrada" + id));
            tarefaUpdateConverter.updateTarefas(dto, entity);
            return tarefaConverter.paraTarefaDTO(tarefaRepository.save(entity));
        } catch (ResourceNotFoudException e) {
            throw new ResourceNotFoudException("Erro ao alterar status da tarefa" + e.getCause());
        }
    }
}
