/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.monitoring.bus;

import jim.sums.common.db.MilestoneTemplate;
import jim.sums.common.facade.MilestoneTemplateFacade;

/**
 *
 * @author Adrian Earle
 */
public class MilestoneTemplateEJB {

    public MilestoneTemplate createMilestoneTemplate(MilestoneTemplate mt) {
        MilestoneTemplateFacade mtf = null;
        mtf.create(mt);
        return mt;
    }

    public void removeMilestoneTemplate(MilestoneTemplate mt) {
        MilestoneTemplateFacade mtf = null;
        mtf.remove(mt);
    }

    public MilestoneTemplate editMilestoneTemplate(MilestoneTemplate mt) {
        MilestoneTemplateFacade mtf = null;
        mtf.edit(mt);
        return mt;
    }
}