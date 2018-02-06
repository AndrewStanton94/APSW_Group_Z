/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.monitoring.bus;

import jim.sums.common.db.MilestoneInstance;
import jim.sums.common.facade.MilestoneInstanceFacade;

/**
 *
 * @author Adrian Earle
 */
public class MilestoneInstanceEJB {

    public MilestoneInstance createMilestoneInstance(MilestoneInstance mi) {
        MilestoneInstanceFacade mif = null;
        mif.create(mi);
        return mi;
    }

    public void removeMilestoneInstance(MilestoneInstance mi) {
        MilestoneInstanceFacade mif = null;
        mif.remove(mi);
    }

    public MilestoneInstance editMilestoneInstance(MilestoneInstance mi) {
        MilestoneInstanceFacade mif = null;
        mif.edit(mi);
        return mi;
    }
}