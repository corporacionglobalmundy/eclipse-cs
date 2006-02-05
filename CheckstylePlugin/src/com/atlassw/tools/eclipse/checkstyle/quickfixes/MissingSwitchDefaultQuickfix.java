//============================================================================
//
// Copyright (C) 2002-2006  David Schneider, Lars K�dderitzsch
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
//============================================================================

package com.atlassw.tools.eclipse.checkstyle.quickfixes;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.internal.ui.JavaPluginImages;
import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.graphics.Image;

public class MissingSwitchDefaultQuickfix extends AbstractASTResolution
{

    public Image getImage()
    {
        // TODO Auto-generated method stub
        return JavaPluginImages.get(JavaPluginImages.IMG_CORRECTION_ADD);
    }

    protected ASTVisitor handleGetCorrectingASTVisitor(final ASTRewrite rewrite,
            final IRegion lineInfo)
    {
        // TODO Auto-generated method stub
        return new ASTVisitor()
        {

            public boolean visit(SwitchStatement node)
            {
                int pos = node.getStartPosition();
                if (pos > lineInfo.getOffset()
                        && pos < (lineInfo.getOffset() + lineInfo.getLength()))
                {
                    SwitchCase defNode = node.getAST().newSwitchCase();
                    defNode.setExpression(null);
                    ListRewrite listRewrite = rewrite.getListRewrite(node,
                            SwitchStatement.STATEMENTS_PROPERTY);
                    listRewrite.insertLast(defNode, null);
                    ASTNode comment = listRewrite.getASTRewrite().createStringPlaceholder(
                            "// TODO add default case statements", ASTNode.LINE_COMMENT);
                    listRewrite.insertLast(comment, null);
                    return true;
                }
                return false;
            }
        };
    }

    public String getDescription()
    {
        return "Add default case";
    }

    public String getLabel()
    {
        return "Add default case";
    }
}