/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.academia.plataformaAcademica.repository;

/**
 *
 * @author Eliver Salazar Campo
 */

import org.springframework.data.jpa.repository.JpaRepository;

import com.academia.plataformaAcademica.domain.RolSistema;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolSistema, Long> {

    Optional<RolSistema> findByNombreRol(String nombreRol);
}

