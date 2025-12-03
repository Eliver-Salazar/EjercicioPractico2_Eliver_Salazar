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
import com.academia.plataformaAcademica.repository.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolSistema> listarTodos() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public RolSistema obtenerPorId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RolSistema guardar(RolSistema rol) {
        return rolRepository.save(rol);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        rolRepository.deleteById(id);
    }
}

