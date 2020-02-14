package br.com.participae.transparencia.repositorio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements query filter criteria.
 *
 * Development History:
 *
 * 26/04/2016 - First version developed by Leandro Luque
 * (leandro.luque@gmail.com).
 */
public class FilterCriteria {

    /**
     * The value that the attributes specified in filterBy must have.
     */
    protected String filter;

    /**
     * The attributes that must be filtered.
     */
    protected List<String> filterBy = new ArrayList<String>();

    /**
     * The attributes that will be used for ordering.
     */
    protected List<String> orderBy = new ArrayList<String>();

    /**
     * The initial row to return.
     */
    protected Integer initialRow;

    /**
     * The number of rows to return.
     */
    protected Integer numberOfRows;

    /**
     * Was any filter criteria specified?
     *
     * @return true, if yes. false, otherwise.
     */
    public boolean hasFilter() {
        return filter != null && filter.trim().length() > 0 && !filterBy.isEmpty();
    }

    /**
     * Was an order specified?
     *
     * @return true, if yes. false, otherwise.
     */
    public boolean hasOrder() {
        return !orderBy.isEmpty();
    }

    /**
     * Was any pagination criteria specified?
     *
     * @return true, if yes. false, otherwise.
     */
    public boolean hasPagination() {
        return initialRow >= 0 && numberOfRows > 0;
    }

    /**
     * An enumeration of ordering types.
     */
    public enum Order {
        ASCENDING, DESCENDING
    };
    /**
     * The order.
     */
    protected Order order;

    /**
     * Gets the value that the attributes specified in filterBy must have.
     *
     * @return The value that the attributes specified in filterBy must have.
     */
    public String getFilterValue() {
        return filter;
    }

    /**
     * Sets the value that the attributes specified in filterBy must have.
     *
     * @param filter The value that the attributes specified in filterBy must
     * have.
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Gets the attributes that must be filtered.
     *
     * @return The attributes that must be filtered.
     */
    public List<String> getFilterFields() {
        return filterBy;
    }

    /**
     * Sets the attributes that must be filtered.
     *
     * @param filterBy The attributes that must be filtered.
     */
    public void setFilterBy(List<String> filterBy) {
        this.filterBy = filterBy;
    }

    /**
     * Gets the attributes that will be used for ordering.
     *
     * @return The attributes that will be used for ordering.
     */
    public List<String> getOrderBy() {
        return orderBy;
    }

    /**
     * Sets the attributes that will be used for ordering.
     *
     * @param orderBy The attributes that will be used for ordering.
     */
    public void setOrderBy(List<String> orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Add the attributes that will be used for ordering.
     *
     * @param ordersBy The attributes that will be used for ordering.
     */
    public void addOrderBy(String... ordersBy) {
        this.orderBy.addAll(Arrays.asList(ordersBy));
    }

    /**
     * Gets the order type.
     *
     * @return The order type.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the order type.
     *
     * @param order The order type.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Gets the initial row.
     *
     * @return The initial row.
     */
    public Integer getInitialRow() {
        return initialRow;
    }

    /**
     * Sets the initial row.
     *
     * @param initialRow The initial row.
     */
    public void setInitialRow(Integer initialRow) {
        this.initialRow = initialRow;
    }

    /**
     * Gets the number of rows.
     *
     * @return The number of rows.
     */
    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Sets the number of rows.
     *
     * @param numberOfRows The number of rows.
     */
    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

} // End of class.
