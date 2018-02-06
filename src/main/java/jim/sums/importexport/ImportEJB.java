/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jim.sums.importexport;

import java.io.*;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.internet.MimeUtility;
import jim.sums.common.facade.PersonFacade;

/**
 *
 * @author Link
 */
@Stateless
public class ImportEJB {

    @EJB
    PersonFacade pf;

    public ImportEJB() {
    }

    public void execute() {
//        Person p;
//        try {
//            String ligne2 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("file");
//            FileReader fr = new FileReader(new File(ligne2));
//            BufferedReader br = new BufferedReader(fr);
//            String ligne;
//            while ((ligne = br.readLine()) != null) {
//                String[] inf = ligne.split(":");
//                String[] info = inf[0].split(",");
//                p = new Person(info[3], info[0], info[1], "spar", new Date(), info[2], null);
//                p.setPersonStatus(ps.findByName("student"));
//                br.close();
//                pf.create(p);
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

    private byte[] decode(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        InputStream b64is = MimeUtility.decode(bais, "base64");
        byte[] tmp = new byte[bytes.length];
        int n = b64is.read(tmp);
        byte[] res = new byte[n];
        System.arraycopy(tmp, 0, res, 0, n);
        return res;
    }
}
