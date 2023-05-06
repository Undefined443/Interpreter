package lexer;

public enum TokenType {
    COMMENT,    // 注释
    ID,         // 进一步区分 保留字 常量 函数 和 标识符
    ORIGIN,     // 关键字 origin
    SCALE,      // 关键字 scale
    ROT,        // 关键字 rot
    IS,         // 关键字 is
    TO,         // 关键字 to
    STEP,       // 关键字 step
    DRAW,       // 关键字  draw
    FOR,        // 关键字  for
    FROM,       // 关键字  from
    T,          // 参数 T
    COLOR,      // 关键字  COLOR
    SIZE,       // 关键字  SIZE
    SEMICO,     // 分号
    L_BRACKET,  // 左圆括号
    R_BRACKET,  // 右圆括号
    COMMA,      // 分隔符
    PLUS,       // 加号
    MINUS,      // 减号
    MUL,        // 乘号
    DIV,        // 除号
    POWER,      // 乘方
    FUNC,
    CONST_ID,
    NONTOKEN,   // 文件结束的 Token
    ERRTOKEN
}