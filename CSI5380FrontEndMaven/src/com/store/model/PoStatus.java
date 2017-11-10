package com.store.model;

/**
 * Defines the possible purchase order statuses. A purchase
 * order can have (1) of the following (3) statuses:
 * <ul>
 *  <li>ordered - the initial status when the order is created</li>
 *  <li>processed - the status when the order has been accepted and approved</li>
 *  <li>denied - the status when the order has been rejected, possibly because the credit
 *  card number was invalid or transaction was unauthorized.</li>
 * </ul>
 * @author Kenny Byrd
 */
public enum PoStatus {
    ORDERED, PROCESSED, DENIED
}
