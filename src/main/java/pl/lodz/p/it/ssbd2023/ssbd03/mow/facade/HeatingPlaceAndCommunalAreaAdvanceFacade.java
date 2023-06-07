package pl.lodz.p.it.ssbd2023.ssbd03.mow.facade;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import pl.lodz.p.it.ssbd2023.ssbd03.common.AbstractFacade;
import pl.lodz.p.it.ssbd2023.ssbd03.config.Roles;
import pl.lodz.p.it.ssbd2023.ssbd03.entities.HeatingPlaceAndCommunalAreaAdvance;

import java.time.LocalDate;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class HeatingPlaceAndCommunalAreaAdvanceFacade extends AbstractFacade<HeatingPlaceAndCommunalAreaAdvance> {
    @PersistenceContext(unitName = "ssbd03mowPU")
    private EntityManager em;

    public HeatingPlaceAndCommunalAreaAdvanceFacade() {
        super(HeatingPlaceAndCommunalAreaAdvance.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    @RolesAllowed({Roles.MANAGER})
    public void edit(HeatingPlaceAndCommunalAreaAdvance entity) {
        super.edit(entity);
    }

    @Override
    @RolesAllowed({Roles.MANAGER})
    public void create(HeatingPlaceAndCommunalAreaAdvance entity) {
        super.create(entity);
    }

    @Override
    @RolesAllowed({Roles.MANAGER, Roles.OWNER})
    public HeatingPlaceAndCommunalAreaAdvance find(Object id) {
        return super.find(id);
    }

    @RolesAllowed({Roles.MANAGER})
    public boolean checkIfAdvanceChangeFactorNotModified(Long buildingId, LocalDate date) {
        TypedQuery<HeatingPlaceAndCommunalAreaAdvance> tq = em.createNamedQuery(
                "HeatingPlaceAndCommunalAreaAdvance.getAllHeatingPlaceAndCommunalAreaAdvances",
                HeatingPlaceAndCommunalAreaAdvance.class);
        tq.setParameter("buildingId", buildingId);
        tq.setParameter("date", date);
        return tq.getResultList().isEmpty();
    }
}
