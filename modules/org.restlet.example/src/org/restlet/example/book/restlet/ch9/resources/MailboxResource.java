/*
 * Copyright 2005-2008 Noelios Consulting.
 * 
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the "License"). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.txt See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL HEADER in each file and
 * include the License file at http://www.opensource.org/licenses/cddl1.txt If
 * applicable, add the following below this CDDL HEADER, with the fields
 * enclosed by brackets "[]" replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 */

package org.restlet.example.book.restlet.ch9.resources;

import java.util.Map;
import java.util.TreeMap;

import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Parameter;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.example.book.restlet.ch9.objects.Contact;
import org.restlet.example.book.restlet.ch9.objects.Mail;
import org.restlet.example.book.restlet.ch9.objects.Mailbox;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.resource.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.Variant;
import org.restlet.util.Series;

/**
 * Resource for a mailbox.
 * 
 */
public class MailboxResource extends BaseResource {

    /** The mailbox represented by this resource. */
    private Mailbox mailbox;

    public MailboxResource(Context context, Request request, Response response) {
        super(context, request, response);
        String mailboxId = (String) request.getAttributes().get("mailboxId");
        mailbox = getDAOFactory().getMailboxDAO().getMailboxById(mailboxId);

        if (mailbox != null) {
            getVariants().add(new Variant(MediaType.TEXT_HTML));
        }
    }

    @Override
    public boolean allowDelete() {
        return true;
    }

    @Override
    public boolean allowPost() {
        return true;
    }

    @Override
    public boolean allowPut() {
        return true;
    }

    @Override
    public void acceptRepresentation(Representation entity)
            throws ResourceException {
        Form form = new Form(entity);
        Mail mail = new Mail();
        mail.setStatus(Mail.STATUS_RECEIVED);
        // TODO changer le sender en Contact?
        // Ici boucler sur la liste des contacts et voir si par hasard, le
        // contact existerait.
        form.getFirstValue("senderAddress");
        form.getFirstValue("senderName");

        mail.setMessage(form.getFirstValue("message"));
        mail.setSubject(form.getFirstValue("subject"));
        // form2.add("sendingDate", mail.getSendingDate().toString());
        Series<Parameter> recipients = form.subList("recipient");
        for (Contact recipient : mail.getRecipients()) {
            // TODO ajouter les contacts ou juste les adresses.
            // form2.add("recipient", recipient.getMailAddress());
        }
        //getDAOFactory().getMailboxDAO().createMail(mailbox, mail);
    }

    @Override
    public void removeRepresentations() throws ResourceException {
        getDAOFactory().getMailboxDAO().deleteMailbox(mailbox);
        getResponse().redirectSeeOther(
                getRequest().getResourceRef().getParentRef());
    }

    @Override
    public Representation represent(Variant variant) throws ResourceException {
        Map<String, Object> dataModel = new TreeMap<String, Object>();
        dataModel.put("currentUser", getCurrentUser());
        dataModel.put("mailbox", mailbox);
        dataModel.put("resourceRef", getRequest().getResourceRef());
        dataModel.put("rootRef", getRequest().getRootRef());

        TemplateRepresentation representation = new TemplateRepresentation(
                "mailbox.html", getFmcConfiguration(), dataModel, variant
                        .getMediaType());

        return representation;
    }

    @Override
    public void storeRepresentation(Representation entity)
            throws ResourceException {
        Form form = new Form(entity);
        mailbox.setNickname(form.getFirstValue("nickname"));

        getDAOFactory().getMailboxDAO().updateMailbox(mailbox);
        getResponse().redirectSeeOther(getRequest().getResourceRef());
    }

}
