package com.targetcar.agendador_tarefas.business.mapper;

import com.targetcar.agendador_tarefas.business.dto.TarefasDTO;
import com.targetcar.agendador_tarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefaConverter {

    TarefasEntity paraTarefaEntity(TarefasDTO dto);

    TarefasDTO paraTarefaDTO(TarefasEntity entity);

}
