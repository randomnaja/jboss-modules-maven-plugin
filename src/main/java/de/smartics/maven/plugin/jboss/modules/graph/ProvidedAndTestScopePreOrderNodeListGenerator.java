/*
 * Copyright 2013 smartics, Kronseder & Reiner GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.smartics.maven.plugin.jboss.modules.graph;

import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.util.graph.PreorderNodeListGenerator;

public class ProvidedAndTestScopePreOrderNodeListGenerator extends PreorderNodeListGenerator {

    private boolean ignoreProvidedScope;

    private boolean ignoreTestScope;

    public ProvidedAndTestScopePreOrderNodeListGenerator(boolean ignoreProvidedScope, boolean ignoreTestScope) {
        this.ignoreProvidedScope = ignoreProvidedScope;
        this.ignoreTestScope = ignoreTestScope;
    }

    @Override
    public boolean visitEnter(DependencyNode node) {
        if ( !setVisited( node ) )
        {
            return false;
        }


        Dependency nodeDep = node.getDependency();
        if ( nodeDep != null )
        {
            if (nodeDep.getScope().equals("provided")
                    && ignoreProvidedScope) {
                return false;
            }

            if (nodeDep.getScope().equals("test") &&
                    ignoreTestScope) {
                return false;
            }

            nodes.add( node );
        }

        return true;
    }
}
