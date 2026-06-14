package com.jobhorizon.backend.ofertatrabajo;

import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class OfertaTrabajoSpecification {

    public static Specification<OfertaTrabajo> conEstado(String estadoNombre) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("estadoOferta").get("nombre")), estadoNombre.toLowerCase());
    }

    public static Specification<OfertaTrabajo> conTituloODescripcion(String text) {
        return (root, query, cb) -> {
            if (text == null || text.trim().isEmpty()) return null;
            String pattern = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("titulo")), pattern),
                    cb.like(cb.lower(root.get("descripcion")), pattern)
            );
        };
    }

    public static Specification<OfertaTrabajo> conTipoContrato(Integer idTipoContrato) {
        return (root, query, cb) -> {
            if (idTipoContrato == null) return null;
            return cb.equal(root.get("tipoContrato").get("id"), idTipoContrato);
        };
    }

    public static Specification<OfertaTrabajo> conModalidad(Integer idModalidad) {
        return (root, query, cb) -> {
            if (idModalidad == null) return null;
            return cb.equal(root.get("modalidad").get("id"), idModalidad);
        };
    }

    public static Specification<OfertaTrabajo> conNivelEducativo(Integer idNivelEducativo) {
        return (root, query, cb) -> {
            if (idNivelEducativo == null) return null;
            return cb.equal(root.get("nivelEducativo").get("id"), idNivelEducativo);
        };
    }

    public static Specification<OfertaTrabajo> conDistrito(Integer idDistrito) {
        return (root, query, cb) -> {
            if (idDistrito == null) return null;
            return cb.equal(root.get("distrito").get("id"), idDistrito);
        };
    }

    public static Specification<OfertaTrabajo> conSalarioMayorOIgualA(BigDecimal salario) {
        return (root, query, cb) -> {
            if (salario == null) return null;
            return cb.or(
                    cb.isNull(root.get("salarioMax")),
                    cb.greaterThanOrEqualTo(root.get("salarioMax"), salario)
            );
        };
    }

    public static Specification<OfertaTrabajo> conExperienciaMenorOIgualA(Short experiencia) {
        return (root, query, cb) -> {
            if (experiencia == null) return null;
            return cb.lessThanOrEqualTo(root.get("aniosExperienciaMinima"), experiencia);
        };
    }

    public static Specification<OfertaTrabajo> conHabilidades(List<Integer> idHabilidades) {
        return (root, query, cb) -> {
            if (idHabilidades == null || idHabilidades.isEmpty()) return null;
            query.distinct(true);
            return root.join("habilidades").get("habilidad").get("id").in(idHabilidades);
        };
    }

    public static Specification<OfertaTrabajo> conIdiomas(List<Integer> idIdiomas) {
        return (root, query, cb) -> {
            if (idIdiomas == null || idIdiomas.isEmpty()) return null;
            query.distinct(true);
            return root.join("idiomas").get("idioma").get("id").in(idIdiomas);
        };
    }
}
