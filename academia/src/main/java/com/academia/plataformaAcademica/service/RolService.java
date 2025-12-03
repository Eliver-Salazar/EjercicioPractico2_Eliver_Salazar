/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.academia.plataformaAcademica.service;

/**
 *
 * @author Eliver Salazar Campo
 */

import com.academia.plataformaAcademica.domain.RolSistema;

import java.util.List;

public interface RolService {

    List<RolSistema> listarTodos();

    RolSistema obtenerPorId(Long id);

    RolSistema guardar(RolSistema rol);

    void eliminar(Long id);
}

