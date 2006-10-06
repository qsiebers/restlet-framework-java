/*
 * Copyright 2005-2006 Noelios Consulting.
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the "License").  You may not use this file except
 * in compliance with the License.
 *
 * You can obtain a copy of the license at
 * http://www.opensource.org/licenses/cddl1.txt
 * See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * HEADER in each file and include the License file at
 * http://www.opensource.org/licenses/cddl1.txt
 * If applicable, add the following below this CDDL
 * HEADER, with the fields enclosed by brackets "[]"
 * replaced with your own identifying information:
 * Portions Copyright [yyyy] [name of copyright owner]
 */

package com.noelios.restlet.example.tutorial;

import org.restlet.Context;
import org.restlet.component.Container;
import org.restlet.data.Protocol;

import com.noelios.restlet.RedirectRestlet;

/**
 * URI rewriting and redirection.
 * @author Jerome Louvel (contact@noelios.com) <a href="http://www.noelios.com/">Noelios Consulting</a>
 */
public class Tutorial10
{
   public static void main(String[] args) throws Exception
   {
      // Create a new Restlet container
      Container myContainer = new Container();
      Context myContext = myContainer.getContext();

      // Add an HTTP server connector to the Restlet container. 
      // Note that the container is the call restlet.
      myContainer.getServers().add(Protocol.HTTP, 8182);

      // Create a redirect Restlet then attach it to the container
      String target = "http://www.google.com/search?q=site:mysite.org+${query('query')}";
      RedirectRestlet redirect = new RedirectRestlet(myContext, target, RedirectRestlet.MODE_CLIENT_TEMPORARY);
      myContainer.getLocalHost().attach("/search", redirect);

      // Now, let's start the container!
      myContainer.start();
   }

}
