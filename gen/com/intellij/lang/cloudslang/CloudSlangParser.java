// This is a generated file. Not intended for manual editing.
package com.intellij.lang.cloudslang;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.intellij.lang.cloudslang.plugin.psi.CloudSlangTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class CloudSlangParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b, 0);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return simpleFile(b, l + 1);
  }

  /* ********************************************************** */
  // namespace_ operation_
  static boolean body_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "body_")) return false;
    if (!nextTokenIs(b, NAMESPACE_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = namespace_(b, l + 1);
    r = r && operation_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // body_|COMMENT|CRLF
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = body_(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CRLF);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OPERATION_JAVA_ACTION_CLASS_NAME_KEYWORD NAMESPACE_SEPARATOR SPACE NAMESPACE_VALUE
  static boolean java_action_class_name_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "java_action_class_name_")) return false;
    if (!nextTokenIs(b, OPERATION_JAVA_ACTION_CLASS_NAME_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, OPERATION_JAVA_ACTION_CLASS_NAME_KEYWORD, NAMESPACE_SEPARATOR, SPACE, NAMESPACE_VALUE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OPERATION_JAVA_ACTION_KEYWORD NAMESPACE_SEPARATOR
  static boolean java_action_header_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "java_action_header_")) return false;
    if (!nextTokenIs(b, OPERATION_JAVA_ACTION_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, OPERATION_JAVA_ACTION_KEYWORD, NAMESPACE_SEPARATOR);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OPERATION_JAVA_ACTION_METHOD_NAME_KEYWORD NAMESPACE_SEPARATOR SPACE NAMESPACE_VALUE
  static boolean java_action_method_name_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "java_action_method_name_")) return false;
    if (!nextTokenIs(b, OPERATION_JAVA_ACTION_METHOD_NAME_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, OPERATION_JAVA_ACTION_METHOD_NAME_KEYWORD, NAMESPACE_SEPARATOR, SPACE, NAMESPACE_VALUE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // NAMESPACE_KEYWORD NAMESPACE_SEPARATOR SPACE NAMESPACE_VALUE
  static boolean namespace_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespace_")) return false;
    if (!nextTokenIs(b, NAMESPACE_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, NAMESPACE_KEYWORD, NAMESPACE_SEPARATOR, SPACE, NAMESPACE_VALUE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OPERATION_KEYWORD NAMESPACE_SEPARATOR operation_name_ operation_inputs_ operation_action_
  static boolean operation_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_")) return false;
    if (!nextTokenIs(b, OPERATION_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, OPERATION_KEYWORD, NAMESPACE_SEPARATOR);
    r = r && operation_name_(b, l + 1);
    r = r && operation_inputs_(b, l + 1);
    r = r && operation_action_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // operation_action_header_ java_action_header_ java_action_class_name_ java_action_method_name_
  static boolean operation_action_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_action_")) return false;
    if (!nextTokenIs(b, OPERATION_ACTION_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = operation_action_header_(b, l + 1);
    r = r && java_action_header_(b, l + 1);
    r = r && java_action_class_name_(b, l + 1);
    r = r && java_action_method_name_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OPERATION_ACTION_KEYWORD NAMESPACE_SEPARATOR
  static boolean operation_action_header_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_action_header_")) return false;
    if (!nextTokenIs(b, OPERATION_ACTION_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, OPERATION_ACTION_KEYWORD, NAMESPACE_SEPARATOR);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // MINUS SPACE NAMESPACE_VALUE
  static boolean operation_input_el_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_input_el_")) return false;
    if (!nextTokenIs(b, MINUS)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, MINUS, SPACE, NAMESPACE_VALUE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // operation_input_el_*
  static boolean operation_input_list_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_input_list_")) return false;
    int c = current_position_(b);
    while (true) {
      if (!operation_input_el_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "operation_input_list_", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // operation_inputs_header_ operation_input_list_
  static boolean operation_inputs_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_inputs_")) return false;
    if (!nextTokenIs(b, OPERATION_INPUTS_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = operation_inputs_header_(b, l + 1);
    r = r && operation_input_list_(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OPERATION_INPUTS_KEYWORD NAMESPACE_SEPARATOR
  static boolean operation_inputs_header_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_inputs_header_")) return false;
    if (!nextTokenIs(b, OPERATION_INPUTS_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, OPERATION_INPUTS_KEYWORD, NAMESPACE_SEPARATOR);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // OPERATION_NAME_KEYWORD NAMESPACE_SEPARATOR SPACE NAMESPACE_VALUE
  static boolean operation_name_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_name_")) return false;
    if (!nextTokenIs(b, OPERATION_NAME_KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, OPERATION_NAME_KEYWORD, NAMESPACE_SEPARATOR, SPACE, NAMESPACE_VALUE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean simpleFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simpleFile")) return false;
    int c = current_position_(b);
    while (true) {
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "simpleFile", c)) break;
      c = current_position_(b);
    }
    return true;
  }

}
