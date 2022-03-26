package dev.tuzserik.service.oriented.architecture.lab2.service.one.utils;

import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.Coordinates;
import dev.tuzserik.service.oriented.architecture.lab2.service.one.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private static TypedQuery<Vehicle> buildQuery(Session session,
                                                  CriteriaBuilder builder,
                                                  CriteriaQuery<Vehicle> query,
                                                  QueryParameters parameters) {
        Root<Vehicle> root = query.from(Vehicle.class);
        Join<Vehicle, Coordinates> join = root.join("coordinates");
        List<Predicate> predicates = new ArrayList<>();

        if (parameters.getId() != null)
            switch (parameters.getIdSign()) {
                case LS:
                    predicates.add(builder.lessThan(root.get("id"), parameters.getId()));
                    break;
                case LE:
                    predicates.add(builder.lessThanOrEqualTo(root.get("id"), parameters.getId()));
                    break;
                case EQ:
                    predicates.add(builder.equal(root.get("id"), parameters.getId()));
                    break;
                case GE:
                    predicates.add(builder.greaterThanOrEqualTo(root.get("id"), parameters.getId()));
                    break;
                case GR:
                    predicates.add(builder.greaterThan(root.get("id"), parameters.getId()));
                    break;
            }

        if (parameters.getName() != null)
            if (parameters.getNameSign().equals(ComparisonSign.LK)) {
                predicates.add(builder.like(root.get("name"), parameters.getName()));
            }
            else {
                predicates.add(builder.like(root.get("name"), "%" + parameters.getName() + "%"));
            }

        if (parameters.getCoordinateX() != null)
            switch (parameters.getXSign()) {
                case LS:
                    predicates.add(builder.lessThan(join.get("x"), parameters.getCoordinateX()));
                    break;
                case LE:
                    predicates.add(builder.lessThanOrEqualTo(join.get("x"), parameters.getCoordinateX()));
                    break;
                case EQ:
                    predicates.add(builder.equal(join.get("x"), parameters.getCoordinateX()));
                    break;
                case GE:
                    predicates.add(builder.greaterThanOrEqualTo(join.get("x"), parameters.getCoordinateX()));
                    break;
                case GR:
                    predicates.add(builder.greaterThan(join.get("x"), parameters.getCoordinateX()));
                    break;
            }

        if (parameters.getCoordinateY() != null)
            switch (parameters.getYSign()) {
                case LS:
                    predicates.add(builder.lessThan(join.get("y"), parameters.getCoordinateY()));
                    break;
                case LE:
                    predicates.add(builder.lessThanOrEqualTo(join.get("y"), parameters.getCoordinateY()));
                    break;
                case EQ:
                    predicates.add(builder.equal(join.get("y"), parameters.getCoordinateY()));
                    break;
                case GE:
                    predicates.add(builder.greaterThanOrEqualTo(join.get("y"), parameters.getCoordinateY()));
                    break;
                case GR:
                    predicates.add(builder.greaterThan(join.get("y"), parameters.getCoordinateY()));
                    break;
            }

        if (parameters.getEnginePower() != null)
            switch (parameters.getEngineSign()) {
                case LS:
                    predicates.add(builder.lessThan(root.get("enginePower"), parameters.getEnginePower()));
                    break;
                case LE:
                    predicates.add(builder.lessThanOrEqualTo(root.get("enginePower"), parameters.getEnginePower()));
                    break;
                case EQ:
                    predicates.add(builder.equal(root.get("enginePower"), parameters.getEnginePower()));
                    break;
                case GE:
                    predicates.add(builder.greaterThanOrEqualTo(root.get("enginePower"), parameters.getEnginePower()));
                    break;
                case GR:
                    predicates.add(builder.greaterThan(root.get("enginePower"), parameters.getEnginePower()));
                    break;
            }

        if (parameters.getNumberOfWheels() != null)
            switch (parameters.getWheelsSign()) {
                case LS:
                    predicates.add(builder.lessThan(root.get("numberOfWheels"), parameters.getNumberOfWheels()));
                    break;
                case LE:
                    predicates.add(builder.lessThanOrEqualTo(root.get("numberOfWheels"), parameters.getNumberOfWheels()));
                    break;
                case EQ:
                    predicates.add(builder.equal(root.get("numberOfWheels"), parameters.getNumberOfWheels()));
                    break;
                case GE:
                    predicates.add(builder.greaterThanOrEqualTo(root.get("numberOfWheels"), parameters.getNumberOfWheels()));
                    break;
                case GR:
                    predicates.add(builder.greaterThan(root.get("numberOfWheels"), parameters.getNumberOfWheels()));
                    break;
            }

        if (parameters.getDistanceTravelled() != null)
            switch (parameters.getDistanceSign()) {
                case LS:
                    predicates.add(builder.lessThan(root.get("distanceTravelled"), parameters.getDistanceTravelled()));
                    break;
                case LE:
                    predicates.add(builder.lessThanOrEqualTo(root.get("distanceTravelled"), parameters.getDistanceTravelled()));
                    break;
                case EQ:
                    predicates.add(builder.equal(root.get("distanceTravelled"), parameters.getDistanceTravelled()));
                    break;
                case GE:
                    predicates.add(builder.greaterThanOrEqualTo(root.get("distanceTravelled"), parameters.getDistanceTravelled()));
                    break;
                case GR:
                    predicates.add(builder.greaterThan(root.get("distanceTravelled"), parameters.getDistanceTravelled()));
                    break;
            }

        if (parameters.getFuelType() != null)
            predicates.add(builder.equal(root.get("fuelType"), parameters.getFuelType().ordinal()));

        CriteriaQuery<Vehicle> builtQuery = query.select(root).where(predicates.toArray(new Predicate[0]));

        if (parameters.getSortField() != null && parameters.getIsDesc() != null)
            builtQuery.orderBy(!parameters.getIsDesc() ?
                    builder.asc(root.get(parameters.getSortField())) : builder.desc(root.get(parameters.getSortField())));

        Query<Vehicle> typedQuery = session.createQuery(builtQuery);

        if (parameters.getPage() == null)
            parameters.setPage(0);
        if (parameters.getLimit() == null)
            parameters.setLimit(10);

        typedQuery.setFirstResult(parameters.getPage() * parameters.getLimit());
        typedQuery.setMaxResults(parameters.getLimit());

        return typedQuery;
    }

    public static TypedQuery<Vehicle> getPreparedQuery(QueryParameters parameters, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = builder.createQuery(Vehicle.class);

        return buildQuery(session, builder, query, parameters);
    }

    public static TypedQuery<Vehicle> getPreparedQuerySingleId(Session session, long id) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = builder.createQuery(Vehicle.class);

        return buildQuery(session, builder, query,
                new QueryParameters().setId(id).setIdSign(ComparisonSign.EQ)
        );
    }

    public static TypedQuery<Double> getPreparedQueryDistanceSum(Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Double> query = builder.createQuery(Double.class);
        Root<Vehicle> root = query.from(Vehicle.class);
        CriteriaQuery<Double> builtQuery = query.select(builder.sum(root.get("distanceTravelled")));
        return session.createQuery(builtQuery);
    }

    public static TypedQuery<Vehicle> getPreparedQueryDistanceTraveledLessThan(Session session, Double distanceTravelled) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = builder.createQuery(Vehicle.class);

        return buildQuery(session, builder, query,
                new QueryParameters().setDistanceTravelled(distanceTravelled).setDistanceSign(ComparisonSign.LS));
    }

    public static TypedQuery<Vehicle> getPreparedQueryWheelsNumberEqual(Session session, Integer wheelsNumber) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Vehicle> query = builder.createQuery(Vehicle.class);

        return buildQuery(session, builder, query,
                new QueryParameters().setNumberOfWheels(wheelsNumber).setWheelsSign(ComparisonSign.EQ));
    }
}
