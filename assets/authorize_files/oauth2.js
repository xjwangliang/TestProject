(function() {
    var STK = function() {
    var that = {};
    var errorList = [];
    that.inc = function(ns, undepended) {
        return true;
    };
    that.register = function(ns, maker) {
        var NSList = ns.split(".");
        var step = that;
        var k = null;
        while (k = NSList.shift()) {
            if (NSList.length) {
                if (step[k] === undefined) {
                    step[k] = {};
                }
                step = step[k];
            } else {
                if (step[k] === undefined) {
                    try {
                        step[k] = maker(that);
                    } catch (exp) {
                        errorList.push(exp);
                    }
                }
            }
        }
    };
    that.regShort = function(sname, sfun) {
        if (that[sname] !== undefined) {
            throw "[" + sname + "] : short : has been register";
        }
        that[sname] = sfun;
    };
    that.IE = /msie/i.test(navigator.userAgent);
    that.E = function(id) {
        if (typeof id === "string") {
            return document.getElementById(id);
        } else {
            return id;
        }
    };
    that.C = function(tagName) {
        var dom;
        tagName = tagName.toUpperCase();
        if (tagName == "TEXT") {
            dom = document.createTextNode("");
        } else if (tagName == "BUFFER") {
            dom = document.createDocumentFragment();
        } else {
            dom = document.createElement(tagName);
        }
        return dom;
    };
    that.log = function(str) {
        errorList.push("[" + (new Date).getTime() % 1e5 + "]: " + str);
    };
    that.getErrorLogInformationList = function(n) {
        return errorList.splice(0, n || errorList.length);
    };
    return that;
}();

$Import = STK.inc;

    STK.register("kit.dom.parseDOM", function($) {
    return function(list) {
        for (var a in list) {
            if (list[a] && list[a].length == 1) {
                list[a] = list[a][0];
            }
        }
        return list;
    };
});

    STK.register("core.arr.indexOf", function($) {
    return function(oElement, aSource) {
        if (aSource.indexOf) {
            return aSource.indexOf(oElement);
        }
        for (var i = 0, len = aSource.length; i < len; i++) {
            if (aSource[i] === oElement) {
                return i;
            }
        }
        return -1;
    };
});


STK.register("core.arr.inArray", function($) {
    return function(oElement, aSource) {
        return $.core.arr.indexOf(oElement, aSource) > -1;
    };
});


STK.register("core.func.getType", function($) {
    return function(oObject) {
        var _t;
        return ((_t = typeof oObject) == "object" ? oObject == null && "null" || Object.prototype.toString.call(oObject).slice(8, -1) : _t).toLowerCase();
    };
});


STK.register("core.dom.sizzle", function($) {
    var chunker = /((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^\[\]]*\]|['"][^'"]*['"]|[^\[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?((?:.|\r|\n)*)/g, done = 0, toString = Object.prototype.toString, hasDuplicate = false, baseHasDuplicate = true;
    [ 0, 0 ].sort(function() {
        baseHasDuplicate = false;
        return 0;
    });
    var Sizzle = function(selector, context, results, seed) {
        results = results || [];
        context = context || document;
        var origContext = context;
        if (context.nodeType !== 1 && context.nodeType !== 9) {
            return [];
        }
        if (!selector || typeof selector !== "string") {
            return results;
        }
        var parts = [], m, set, checkSet, extra, prune = true, contextXML = Sizzle.isXML(context), soFar = selector, ret, cur, pop, i;
        do {
            chunker.exec("");
            m = chunker.exec(soFar);
            if (m) {
                soFar = m[3];
                parts.push(m[1]);
                if (m[2]) {
                    extra = m[3];
                    break;
                }
            }
        } while (m);
        if (parts.length > 1 && origPOS.exec(selector)) {
            if (parts.length === 2 && Expr.relative[parts[0]]) {
                set = posProcess(parts[0] + parts[1], context);
            } else {
                set = Expr.relative[parts[0]] ? [ context ] : Sizzle(parts.shift(), context);
                while (parts.length) {
                    selector = parts.shift();
                    if (Expr.relative[selector]) {
                        selector += parts.shift();
                    }
                    set = posProcess(selector, set);
                }
            }
        } else {
            if (!seed && parts.length > 1 && context.nodeType === 9 && !contextXML && Expr.match.ID.test(parts[0]) && !Expr.match.ID.test(parts[parts.length - 1])) {
                ret = Sizzle.find(parts.shift(), context, contextXML);
                context = ret.expr ? Sizzle.filter(ret.expr, ret.set)[0] : ret.set[0];
            }
            if (context) {
                ret = seed ? {
                    expr: parts.pop(),
                    set: makeArray(seed)
                } : Sizzle.find(parts.pop(), parts.length === 1 && (parts[0] === "~" || parts[0] === "+") && context.parentNode ? context.parentNode : context, contextXML);
                set = ret.expr ? Sizzle.filter(ret.expr, ret.set) : ret.set;
                if (parts.length > 0) {
                    checkSet = makeArray(set);
                } else {
                    prune = false;
                }
                while (parts.length) {
                    cur = parts.pop();
                    pop = cur;
                    if (!Expr.relative[cur]) {
                        cur = "";
                    } else {
                        pop = parts.pop();
                    }
                    if (pop == null) {
                        pop = context;
                    }
                    Expr.relative[cur](checkSet, pop, contextXML);
                }
            } else {
                checkSet = parts = [];
            }
        }
        if (!checkSet) {
            checkSet = set;
        }
        if (!checkSet) {
            Sizzle.error(cur || selector);
        }
        if (toString.call(checkSet) === "[object Array]") {
            if (!prune) {
                results.push.apply(results, checkSet);
            } else if (context && context.nodeType === 1) {
                for (i = 0; checkSet[i] != null; i++) {
                    if (checkSet[i] && (checkSet[i] === true || checkSet[i].nodeType === 1 && Sizzle.contains(context, checkSet[i]))) {
                        results.push(set[i]);
                    }
                }
            } else {
                for (i = 0; checkSet[i] != null; i++) {
                    if (checkSet[i] && checkSet[i].nodeType === 1) {
                        results.push(set[i]);
                    }
                }
            }
        } else {
            makeArray(checkSet, results);
        }
        if (extra) {
            Sizzle(extra, origContext, results, seed);
            Sizzle.uniqueSort(results);
        }
        return results;
    };
    Sizzle.uniqueSort = function(results) {
        if (sortOrder) {
            hasDuplicate = baseHasDuplicate;
            results.sort(sortOrder);
            if (hasDuplicate) {
                for (var i = 1; i < results.length; i++) {
                    if (results[i] === results[i - 1]) {
                        results.splice(i--, 1);
                    }
                }
            }
        }
        return results;
    };
    Sizzle.matches = function(expr, set) {
        return Sizzle(expr, null, null, set);
    };
    Sizzle.find = function(expr, context, isXML) {
        var set;
        if (!expr) {
            return [];
        }
        for (var i = 0, l = Expr.order.length; i < l; i++) {
            var type = Expr.order[i], match;
            if (match = Expr.leftMatch[type].exec(expr)) {
                var left = match[1];
                match.splice(1, 1);
                if (left.substr(left.length - 1) !== "\\") {
                    match[1] = (match[1] || "").replace(/\\/g, "");
                    set = Expr.find[type](match, context, isXML);
                    if (set != null) {
                        expr = expr.replace(Expr.match[type], "");
                        break;
                    }
                }
            }
        }
        if (!set) {
            set = context.getElementsByTagName("*");
        }
        return {
            set: set,
            expr: expr
        };
    };
    Sizzle.filter = function(expr, set, inplace, not) {
        var old = expr, result = [], curLoop = set, match, anyFound, isXMLFilter = set && set[0] && Sizzle.isXML(set[0]);
        while (expr && set.length) {
            for (var type in Expr.filter) {
                if ((match = Expr.leftMatch[type].exec(expr)) != null && match[2]) {
                    var filter = Expr.filter[type], found, item, left = match[1];
                    anyFound = false;
                    match.splice(1, 1);
                    if (left.substr(left.length - 1) === "\\") {
                        continue;
                    }
                    if (curLoop === result) {
                        result = [];
                    }
                    if (Expr.preFilter[type]) {
                        match = Expr.preFilter[type](match, curLoop, inplace, result, not, isXMLFilter);
                        if (!match) {
                            anyFound = found = true;
                        } else if (match === true) {
                            continue;
                        }
                    }
                    if (match) {
                        for (var i = 0; (item = curLoop[i]) != null; i++) {
                            if (item) {
                                found = filter(item, match, i, curLoop);
                                var pass = not ^ !!found;
                                if (inplace && found != null) {
                                    if (pass) {
                                        anyFound = true;
                                    } else {
                                        curLoop[i] = false;
                                    }
                                } else if (pass) {
                                    result.push(item);
                                    anyFound = true;
                                }
                            }
                        }
                    }
                    if (found !== undefined) {
                        if (!inplace) {
                            curLoop = result;
                        }
                        expr = expr.replace(Expr.match[type], "");
                        if (!anyFound) {
                            return [];
                        }
                        break;
                    }
                }
            }
            if (expr === old) {
                if (anyFound == null) {
                    Sizzle.error(expr);
                } else {
                    break;
                }
            }
            old = expr;
        }
        return curLoop;
    };
    Sizzle.error = function(msg) {
        throw "Syntax error, unrecognized expression: " + msg;
    };
    var Expr = {
        order: [ "ID", "NAME", "TAG" ],
        match: {
            ID: /#((?:[\w\u00c0-\uFFFF\-]|\\.)+)/,
            CLASS: /\.((?:[\w\u00c0-\uFFFF\-]|\\.)+)/,
            NAME: /\[name=['"]*((?:[\w\u00c0-\uFFFF\-]|\\.)+)['"]*\]/,
            ATTR: /\[\s*((?:[\w\u00c0-\uFFFF\-]|\\.)+)\s*(?:(\S?=)\s*(['"]*)(.*?)\3|)\s*\]/,
            TAG: /^((?:[\w\u00c0-\uFFFF\*\-]|\\.)+)/,
            CHILD: /:(only|nth|last|first)-child(?:\((even|odd|[\dn+\-]*)\))?/,
            POS: /:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^\-]|$)/,
            PSEUDO: /:((?:[\w\u00c0-\uFFFF\-]|\\.)+)(?:\((['"]?)((?:\([^\)]+\)|[^\(\)]*)+)\2\))?/
        },
        leftMatch: {},
        attrMap: {
            "class": "className",
            "for": "htmlFor"
        },
        attrHandle: {
            href: function(elem) {
                return elem.getAttribute("href");
            }
        },
        relative: {
            "+": function(checkSet, part) {
                var isPartStr = typeof part === "string", isTag = isPartStr && !/\W/.test(part), isPartStrNotTag = isPartStr && !isTag;
                if (isTag) {
                    part = part.toLowerCase();
                }
                for (var i = 0, l = checkSet.length, elem; i < l; i++) {
                    if (elem = checkSet[i]) {
                        while ((elem = elem.previousSibling) && elem.nodeType !== 1) {}
                        checkSet[i] = isPartStrNotTag || elem && elem.nodeName.toLowerCase() === part ? elem || false : elem === part;
                    }
                }
                if (isPartStrNotTag) {
                    Sizzle.filter(part, checkSet, true);
                }
            },
            ">": function(checkSet, part) {
                var isPartStr = typeof part === "string", elem, i = 0, l = checkSet.length;
                if (isPartStr && !/\W/.test(part)) {
                    part = part.toLowerCase();
                    for (; i < l; i++) {
                        elem = checkSet[i];
                        if (elem) {
                            var parent = elem.parentNode;
                            checkSet[i] = parent.nodeName.toLowerCase() === part ? parent : false;
                        }
                    }
                } else {
                    for (; i < l; i++) {
                        elem = checkSet[i];
                        if (elem) {
                            checkSet[i] = isPartStr ? elem.parentNode : elem.parentNode === part;
                        }
                    }
                    if (isPartStr) {
                        Sizzle.filter(part, checkSet, true);
                    }
                }
            },
            "": function(checkSet, part, isXML) {
                var doneName = done++, checkFn = dirCheck, nodeCheck;
                if (typeof part === "string" && !/\W/.test(part)) {
                    part = part.toLowerCase();
                    nodeCheck = part;
                    checkFn = dirNodeCheck;
                }
                checkFn("parentNode", part, doneName, checkSet, nodeCheck, isXML);
            },
            "~": function(checkSet, part, isXML) {
                var doneName = done++, checkFn = dirCheck, nodeCheck;
                if (typeof part === "string" && !/\W/.test(part)) {
                    part = part.toLowerCase();
                    nodeCheck = part;
                    checkFn = dirNodeCheck;
                }
                checkFn("previousSibling", part, doneName, checkSet, nodeCheck, isXML);
            }
        },
        find: {
            ID: function(match, context, isXML) {
                if (typeof context.getElementById !== "undefined" && !isXML) {
                    var m = context.getElementById(match[1]);
                    return m ? [ m ] : [];
                }
            },
            NAME: function(match, context) {
                if (typeof context.getElementsByName !== "undefined") {
                    var ret = [], results = context.getElementsByName(match[1]);
                    for (var i = 0, l = results.length; i < l; i++) {
                        if (results[i].getAttribute("name") === match[1]) {
                            ret.push(results[i]);
                        }
                    }
                    return ret.length === 0 ? null : ret;
                }
            },
            TAG: function(match, context) {
                return context.getElementsByTagName(match[1]);
            }
        },
        preFilter: {
            CLASS: function(match, curLoop, inplace, result, not, isXML) {
                match = " " + match[1].replace(/\\/g, "") + " ";
                if (isXML) {
                    return match;
                }
                for (var i = 0, elem; (elem = curLoop[i]) != null; i++) {
                    if (elem) {
                        if (not ^ (elem.className && (" " + elem.className + " ").replace(/[\t\n]/g, " ").indexOf(match) >= 0)) {
                            if (!inplace) {
                                result.push(elem);
                            }
                        } else if (inplace) {
                            curLoop[i] = false;
                        }
                    }
                }
                return false;
            },
            ID: function(match) {
                return match[1].replace(/\\/g, "");
            },
            TAG: function(match, curLoop) {
                return match[1].toLowerCase();
            },
            CHILD: function(match) {
                if (match[1] === "nth") {
                    var test = /(-?)(\d*)n((?:\+|-)?\d*)/.exec(match[2] === "even" && "2n" || match[2] === "odd" && "2n+1" || !/\D/.test(match[2]) && "0n+" + match[2] || match[2]);
                    match[2] = test[1] + (test[2] || 1) - 0;
                    match[3] = test[3] - 0;
                }
                match[0] = done++;
                return match;
            },
            ATTR: function(match, curLoop, inplace, result, not, isXML) {
                var name = match[1].replace(/\\/g, "");
                if (!isXML && Expr.attrMap[name]) {
                    match[1] = Expr.attrMap[name];
                }
                if (match[2] === "~=") {
                    match[4] = " " + match[4] + " ";
                }
                return match;
            },
            PSEUDO: function(match, curLoop, inplace, result, not) {
                if (match[1] === "not") {
                    if ((chunker.exec(match[3]) || "").length > 1 || /^\w/.test(match[3])) {
                        match[3] = Sizzle(match[3], null, null, curLoop);
                    } else {
                        var ret = Sizzle.filter(match[3], curLoop, inplace, true ^ not);
                        if (!inplace) {
                            result.push.apply(result, ret);
                        }
                        return false;
                    }
                } else if (Expr.match.POS.test(match[0]) || Expr.match.CHILD.test(match[0])) {
                    return true;
                }
                return match;
            },
            POS: function(match) {
                match.unshift(true);
                return match;
            }
        },
        filters: {
            enabled: function(elem) {
                return elem.disabled === false && elem.type !== "hidden";
            },
            disabled: function(elem) {
                return elem.disabled === true;
            },
            checked: function(elem) {
                return elem.checked === true;
            },
            selected: function(elem) {
                elem.parentNode.selectedIndex;
                return elem.selected === true;
            },
            parent: function(elem) {
                return !!elem.firstChild;
            },
            empty: function(elem) {
                return !elem.firstChild;
            },
            has: function(elem, i, match) {
                return !!Sizzle(match[3], elem).length;
            },
            header: function(elem) {
                return /h\d/i.test(elem.nodeName);
            },
            text: function(elem) {
                return "text" === elem.type;
            },
            radio: function(elem) {
                return "radio" === elem.type;
            },
            checkbox: function(elem) {
                return "checkbox" === elem.type;
            },
            file: function(elem) {
                return "file" === elem.type;
            },
            password: function(elem) {
                return "password" === elem.type;
            },
            submit: function(elem) {
                return "submit" === elem.type;
            },
            image: function(elem) {
                return "image" === elem.type;
            },
            reset: function(elem) {
                return "reset" === elem.type;
            },
            button: function(elem) {
                return "button" === elem.type || elem.nodeName.toLowerCase() === "button";
            },
            input: function(elem) {
                return /input|select|textarea|button/i.test(elem.nodeName);
            }
        },
        setFilters: {
            first: function(elem, i) {
                return i === 0;
            },
            last: function(elem, i, match, array) {
                return i === array.length - 1;
            },
            even: function(elem, i) {
                return i % 2 === 0;
            },
            odd: function(elem, i) {
                return i % 2 === 1;
            },
            lt: function(elem, i, match) {
                return i < match[3] - 0;
            },
            gt: function(elem, i, match) {
                return i > match[3] - 0;
            },
            nth: function(elem, i, match) {
                return match[3] - 0 === i;
            },
            eq: function(elem, i, match) {
                return match[3] - 0 === i;
            }
        },
        filter: {
            PSEUDO: function(elem, match, i, array) {
                var name = match[1], filter = Expr.filters[name];
                if (filter) {
                    return filter(elem, i, match, array);
                } else if (name === "contains") {
                    return (elem.textContent || elem.innerText || Sizzle.getText([ elem ]) || "").indexOf(match[3]) >= 0;
                } else if (name === "not") {
                    var not = match[3];
                    for (var j = 0, l = not.length; j < l; j++) {
                        if (not[j] === elem) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    Sizzle.error("Syntax error, unrecognized expression: " + name);
                }
            },
            CHILD: function(elem, match) {
                var type = match[1], node = elem;
                switch (type) {
                  case "only":
                  case "first":
                    while (node = node.previousSibling) {
                        if (node.nodeType === 1) {
                            return false;
                        }
                    }
                    if (type === "first") {
                        return true;
                    }
                    node = elem;
                  case "last":
                    while (node = node.nextSibling) {
                        if (node.nodeType === 1) {
                            return false;
                        }
                    }
                    return true;
                  case "nth":
                    var first = match[2], last = match[3];
                    if (first === 1 && last === 0) {
                        return true;
                    }
                    var doneName = match[0], parent = elem.parentNode;
                    if (parent && (parent.sizcache !== doneName || !elem.nodeIndex)) {
                        var count = 0;
                        for (node = parent.firstChild; node; node = node.nextSibling) {
                            if (node.nodeType === 1) {
                                node.nodeIndex = ++count;
                            }
                        }
                        parent.sizcache = doneName;
                    }
                    var diff = elem.nodeIndex - last;
                    if (first === 0) {
                        return diff === 0;
                    } else {
                        return diff % first === 0 && diff / first >= 0;
                    }
                }
            },
            ID: function(elem, match) {
                return elem.nodeType === 1 && elem.getAttribute("id") === match;
            },
            TAG: function(elem, match) {
                return match === "*" && elem.nodeType === 1 || elem.nodeName.toLowerCase() === match;
            },
            CLASS: function(elem, match) {
                return (" " + (elem.className || elem.getAttribute("class")) + " ").indexOf(match) > -1;
            },
            ATTR: function(elem, match) {
                var name = match[1], result = Expr.attrHandle[name] ? Expr.attrHandle[name](elem) : elem[name] != null ? elem[name] : elem.getAttribute(name), value = result + "", type = match[2], check = match[4];
                return result == null ? type === "!=" : type === "=" ? value === check : type === "*=" ? value.indexOf(check) >= 0 : type === "~=" ? (" " + value + " ").indexOf(check) >= 0 : !check ? value && result !== false : type === "!=" ? value !== check : type === "^=" ? value.indexOf(check) === 0 : type === "$=" ? value.substr(value.length - check.length) === check : type === "|=" ? value === check || value.substr(0, check.length + 1) === check + "-" : false;
            },
            POS: function(elem, match, i, array) {
                var name = match[2], filter = Expr.setFilters[name];
                if (filter) {
                    return filter(elem, i, match, array);
                }
            }
        }
    };
    Sizzle.selectors = Expr;
    var origPOS = Expr.match.POS, fescape = function(all, num) {
        return "\\" + (num - 0 + 1);
    };
    for (var type in Expr.match) {
        Expr.match[type] = new RegExp(Expr.match[type].source + /(?![^\[]*\])(?![^\(]*\))/.source);
        Expr.leftMatch[type] = new RegExp(/(^(?:.|\r|\n)*?)/.source + Expr.match[type].source.replace(/\\(\d+)/g, fescape));
    }
    var makeArray = function(array, results) {
        array = Array.prototype.slice.call(array, 0);
        if (results) {
            results.push.apply(results, array);
            return results;
        }
        return array;
    };
    try {
        Array.prototype.slice.call(document.documentElement.childNodes, 0)[0].nodeType;
    } catch (e) {
        makeArray = function(array, results) {
            var ret = results || [], i = 0;
            if (toString.call(array) === "[object Array]") {
                Array.prototype.push.apply(ret, array);
            } else {
                if (typeof array.length === "number") {
                    for (var l = array.length; i < l; i++) {
                        ret.push(array[i]);
                    }
                } else {
                    for (; array[i]; i++) {
                        ret.push(array[i]);
                    }
                }
            }
            return ret;
        };
    }
    var sortOrder;
    if (document.documentElement.compareDocumentPosition) {
        sortOrder = function(a, b) {
            if (!a.compareDocumentPosition || !b.compareDocumentPosition) {
                if (a == b) {
                    hasDuplicate = true;
                }
                return a.compareDocumentPosition ? -1 : 1;
            }
            var ret = a.compareDocumentPosition(b) & 4 ? -1 : a === b ? 0 : 1;
            if (ret === 0) {
                hasDuplicate = true;
            }
            return ret;
        };
    } else if ("sourceIndex" in document.documentElement) {
        sortOrder = function(a, b) {
            if (!a.sourceIndex || !b.sourceIndex) {
                if (a == b) {
                    hasDuplicate = true;
                }
                return a.sourceIndex ? -1 : 1;
            }
            var ret = a.sourceIndex - b.sourceIndex;
            if (ret === 0) {
                hasDuplicate = true;
            }
            return ret;
        };
    } else if (document.createRange) {
        sortOrder = function(a, b) {
            if (!a.ownerDocument || !b.ownerDocument) {
                if (a == b) {
                    hasDuplicate = true;
                }
                return a.ownerDocument ? -1 : 1;
            }
            var aRange = a.ownerDocument.createRange(), bRange = b.ownerDocument.createRange();
            aRange.setStart(a, 0);
            aRange.setEnd(a, 0);
            bRange.setStart(b, 0);
            bRange.setEnd(b, 0);
            var ret = aRange.compareBoundaryPoints(Range.START_TO_END, bRange);
            if (ret === 0) {
                hasDuplicate = true;
            }
            return ret;
        };
    }
    Sizzle.getText = function(elems) {
        var ret = "", elem;
        for (var i = 0; elems[i]; i++) {
            elem = elems[i];
            if (elem.nodeType === 3 || elem.nodeType === 4) {
                ret += elem.nodeValue;
            } else if (elem.nodeType !== 8) {
                ret += Sizzle.getText(elem.childNodes);
            }
        }
        return ret;
    };
    (function() {
        var form = document.createElement("div"), id = "script" + (new Date).getTime();
        form.innerHTML = "<a name='" + id + "'/>";
        var root = document.documentElement;
        root.insertBefore(form, root.firstChild);
        if (document.getElementById(id)) {
            Expr.find.ID = function(match, context, isXML) {
                if (typeof context.getElementById !== "undefined" && !isXML) {
                    var m = context.getElementById(match[1]);
                    return m ? m.id === match[1] || typeof m.getAttributeNode !== "undefined" && m.getAttributeNode("id").nodeValue === match[1] ? [ m ] : undefined : [];
                }
            };
            Expr.filter.ID = function(elem, match) {
                var node = typeof elem.getAttributeNode !== "undefined" && elem.getAttributeNode("id");
                return elem.nodeType === 1 && node && node.nodeValue === match;
            };
        }
        root.removeChild(form);
        root = form = null;
    })();
    (function() {
        var div = document.createElement("div");
        div.appendChild(document.createComment(""));
        if (div.getElementsByTagName("*").length > 0) {
            Expr.find.TAG = function(match, context) {
                var results = context.getElementsByTagName(match[1]);
                if (match[1] === "*") {
                    var tmp = [];
                    for (var i = 0; results[i]; i++) {
                        if (results[i].nodeType === 1) {
                            tmp.push(results[i]);
                        }
                    }
                    results = tmp;
                }
                return results;
            };
        }
        div.innerHTML = "<a href='#'></a>";
        if (div.firstChild && typeof div.firstChild.getAttribute !== "undefined" && div.firstChild.getAttribute("href") !== "#") {
            Expr.attrHandle.href = function(elem) {
                return elem.getAttribute("href", 2);
            };
        }
        div = null;
    })();
    if (document.querySelectorAll) {
        (function() {
            var oldSizzle = Sizzle, div = document.createElement("div");
            div.innerHTML = "<p class='TEST'></p>";
            if (div.querySelectorAll && div.querySelectorAll(".TEST").length === 0) {
                return;
            }
            Sizzle = function(query, context, extra, seed) {
                context = context || document;
                if (!seed && context.nodeType === 9 && !Sizzle.isXML(context)) {
                    try {
                        return makeArray(context.querySelectorAll(query), extra);
                    } catch (e) {}
                }
                return oldSizzle(query, context, extra, seed);
            };
            for (var prop in oldSizzle) {
                Sizzle[prop] = oldSizzle[prop];
            }
            div = null;
        })();
    }
    (function() {
        var div = document.createElement("div");
        div.innerHTML = "<div class='test e'></div><div class='test'></div>";
        if (!div.getElementsByClassName || div.getElementsByClassName("e").length === 0) {
            return;
        }
        div.lastChild.className = "e";
        if (div.getElementsByClassName("e").length === 1) {
            return;
        }
        Expr.order.splice(1, 0, "CLASS");
        Expr.find.CLASS = function(match, context, isXML) {
            if (typeof context.getElementsByClassName !== "undefined" && !isXML) {
                return context.getElementsByClassName(match[1]);
            }
        };
        div = null;
    })();
    function dirNodeCheck(dir, cur, doneName, checkSet, nodeCheck, isXML) {
        for (var i = 0, l = checkSet.length; i < l; i++) {
            var elem = checkSet[i];
            if (elem) {
                elem = elem[dir];
                var match = false;
                while (elem) {
                    if (elem.sizcache === doneName) {
                        match = checkSet[elem.sizset];
                        break;
                    }
                    if (elem.nodeType === 1 && !isXML) {
                        elem.sizcache = doneName;
                        elem.sizset = i;
                    }
                    if (elem.nodeName.toLowerCase() === cur) {
                        match = elem;
                        break;
                    }
                    elem = elem[dir];
                }
                checkSet[i] = match;
            }
        }
    }
    function dirCheck(dir, cur, doneName, checkSet, nodeCheck, isXML) {
        for (var i = 0, l = checkSet.length; i < l; i++) {
            var elem = checkSet[i];
            if (elem) {
                elem = elem[dir];
                var match = false;
                while (elem) {
                    if (elem.sizcache === doneName) {
                        match = checkSet[elem.sizset];
                        break;
                    }
                    if (elem.nodeType === 1) {
                        if (!isXML) {
                            elem.sizcache = doneName;
                            elem.sizset = i;
                        }
                        if (typeof cur !== "string") {
                            if (elem === cur) {
                                match = true;
                                break;
                            }
                        } else if (Sizzle.filter(cur, [ elem ]).length > 0) {
                            match = elem;
                            break;
                        }
                    }
                    elem = elem[dir];
                }
                checkSet[i] = match;
            }
        }
    }
    Sizzle.contains = document.compareDocumentPosition ? function(a, b) {
        return !!(a.compareDocumentPosition(b) & 16);
    } : function(a, b) {
        return a !== b && (a.contains ? a.contains(b) : true);
    };
    Sizzle.isXML = function(elem) {
        var documentElement = (elem ? elem.ownerDocument || elem : 0).documentElement;
        return documentElement ? documentElement.nodeName !== "HTML" : false;
    };
    var posProcess = function(selector, context) {
        var tmpSet = [], later = "", match, root = context.nodeType ? [ context ] : context;
        while (match = Expr.match.PSEUDO.exec(selector)) {
            later += match[0];
            selector = selector.replace(Expr.match.PSEUDO, "");
        }
        selector = Expr.relative[selector] ? selector + "*" : selector;
        for (var i = 0, l = root.length; i < l; i++) {
            Sizzle(selector, root[i], tmpSet);
        }
        return Sizzle.filter(later, tmpSet);
    };
    return Sizzle;
});


STK.register("core.dom.builder", function($) {
    function autoDeploy(sHTML, oSelector) {
        if (oSelector) {
            return oSelector;
        }
        var result, re = /\<(\w+)[^>]*\s+node-type\s*=\s*([\'\"])?(\w+)\2.*?>/g;
        var selectorList = {};
        var node, tag, selector;
        while (result = re.exec(sHTML)) {
            tag = result[1];
            node = result[3];
            selector = tag + "[node-type=" + node + "]";
            selectorList[node] = selectorList[node] == null ? [] : selectorList[node];
            if (!$.core.arr.inArray(selector, selectorList[node])) {
                selectorList[node].push(tag + "[node-type=" + node + "]");
            }
        }
        return selectorList;
    }
    return function(sHTML, oSelector) {
        var _isHTML = $.core.func.getType(sHTML) == "string";
        var selectorList = autoDeploy(_isHTML ? sHTML : sHTML.innerHTML, oSelector);
        var container = sHTML;
        if (_isHTML) {
            container = $.C("div");
            container.innerHTML = sHTML;
        }
        var key, domList, totalList;
        totalList = $.core.dom.sizzle("[node-type]", container);
        domList = {};
        for (key in selectorList) {
            domList[key] = $.core.dom.sizzle.matches(selectorList[key].toString(), totalList);
        }
        var domBox = sHTML;
        if (_isHTML) {
            domBox = $.C("buffer");
            while (container.children[0]) {
                domBox.appendChild(container.children[0]);
            }
        }
        return {
            box: domBox,
            list: domList
        };
    };
});

     
    STK.register("core.util.scrollPos", function($) {
    return function(oDocument) {
        oDocument = oDocument || document;
        var dd = oDocument.documentElement;
        var db = oDocument.body;
        return {
            top: Math.max(window.pageYOffset || 0, dd.scrollTop, db.scrollTop),
            left: Math.max(window.pageXOffset || 0, dd.scrollLeft, db.scrollLeft)
        };
    };
});


STK.register("core.util.browser", function($) {
    var ua = navigator.userAgent.toLowerCase();
    var external = window.external || "";
    var core, m, extra, version, os;
    var numberify = function(s) {
        var c = 0;
        return parseFloat(s.replace(/\./g, function() {
            return c++ == 1 ? "" : ".";
        }));
    };
    try {
        if (/windows|win32/i.test(ua)) {
            os = "windows";
        } else if (/macintosh/i.test(ua)) {
            os = "macintosh";
        } else if (/rhino/i.test(ua)) {
            os = "rhino";
        }
        if ((m = ua.match(/applewebkit\/([^\s]*)/)) && m[1]) {
            core = "webkit";
            version = numberify(m[1]);
        } else if ((m = ua.match(/presto\/([\d.]*)/)) && m[1]) {
            core = "presto";
            version = numberify(m[1]);
        } else if (m = ua.match(/msie\s([^;]*)/)) {
            core = "trident";
            version = 1;
            if ((m = ua.match(/trident\/([\d.]*)/)) && m[1]) {
                version = numberify(m[1]);
            }
        } else if (/gecko/.test(ua)) {
            core = "gecko";
            version = 1;
            if ((m = ua.match(/rv:([\d.]*)/)) && m[1]) {
                version = numberify(m[1]);
            }
        }
        if (/world/.test(ua)) {
            extra = "world";
        } else if (/360se/.test(ua)) {
            extra = "360";
        } else if (/maxthon/.test(ua) || typeof external.max_version == "number") {
            extra = "maxthon";
        } else if (/tencenttraveler\s([\d.]*)/.test(ua)) {
            extra = "tt";
        } else if (/se\s([\d.]*)/.test(ua)) {
            extra = "sogou";
        }
    } catch (e) {}
    var ret = {
        OS: os,
        CORE: core,
        Version: version,
        EXTRA: extra ? extra : false,
        IE: /msie/.test(ua),
        OPERA: /opera/.test(ua),
        MOZ: /gecko/.test(ua) && !/(compatible|webkit)/.test(ua),
        IE5: /msie 5 /.test(ua),
        IE55: /msie 5.5/.test(ua),
        IE6: /msie 6/.test(ua),
        IE7: /msie 7/.test(ua),
        IE8: /msie 8/.test(ua),
        IE9: /msie 9/.test(ua),
        SAFARI: !/chrome\/([\d.]*)/.test(ua) && /\/([\d.]*) safari/.test(ua),
        CHROME: /chrome\/([\d.]*)/.test(ua),
        IPAD: /\(ipad/i.test(ua),
        IPHONE: /\(iphone/i.test(ua),
        ITOUCH: /\(itouch/i.test(ua),
        MOBILE: /mobile/i.test(ua)
    };
    return ret;
});


STK.register("core.obj.parseParam", function($) {
    return function(oSource, oParams, isown) {
        var key, obj = {};
        oParams = oParams || {};
        for (key in oSource) {
            obj[key] = oSource[key];
            if (oParams[key] != null) {
                if (isown) {
                    if (oSource.hasOwnProperty[key]) {
                        obj[key] = oParams[key];
                    }
                } else {
                    obj[key] = oParams[key];
                }
            }
        }
        return obj;
    };
});


STK.register("core.dom.position", function($) {
    var generalPosition = function(el) {
        var box, scroll, body, docElem, clientTop, clientLeft;
        box = el.getBoundingClientRect();
        scroll = $.core.util.scrollPos();
        body = el.ownerDocument.body;
        docElem = el.ownerDocument.documentElement;
        clientTop = docElem.clientTop || body.clientTop || 0;
        clientLeft = docElem.clientLeft || body.clientLeft || 0;
        return {
            l: parseInt(box.left + scroll["left"] - clientLeft, 10) || 0,
            t: parseInt(box.top + scroll["top"] - clientTop, 10) || 0
        };
    };
    var countPosition = function(el, shell) {
        var pos;
        pos = [ el.offsetLeft, el.offsetTop ];
        parent = el.offsetParent;
        if (parent !== el && parent !== shell) {
            while (parent) {
                pos[0] += parent.offsetLeft;
                pos[1] += parent.offsetTop;
                parent = parent.offsetParent;
            }
        }
        if ($.core.util.browser.OPERA != -1 || $.core.util.browser.SAFARI != -1 && el.style.position == "absolute") {
            pos[0] -= document.body.offsetLeft;
            pos[1] -= document.body.offsetTop;
        }
        if (el.parentNode) {
            parent = el.parentNode;
        } else {
            parent = null;
        }
        while (parent && !/^body|html$/i.test(parent.tagName) && parent !== shell) {
            if (parent.style.display.search(/^inline|table-row.*$/i)) {
                pos[0] -= parent.scrollLeft;
                pos[1] -= parent.scrollTop;
            }
            parent = parent.parentNode;
        }
        return {
            l: parseInt(pos[0], 10),
            t: parseInt(pos[1], 10)
        };
    };
    return function(oElement, spec) {
        if (oElement == document.body) {
            return false;
        }
        if (oElement.parentNode == null) {
            return false;
        }
        if (oElement.style.display == "none") {
            return false;
        }
        var conf = $.core.obj.parseParam({
            parent: null
        }, spec);
        if (oElement.getBoundingClientRect) {
            if (conf.parent) {
                var o = generalPosition(oElement);
                var p = generalPosition(conf.parent);
                return {
                    l: o.l - p.l,
                    t: o.t - p.t
                };
            } else {
                return generalPosition(oElement);
            }
        } else {
            return countPosition(oElement, conf.parent || document.body);
        }
    };
});

    STK.register("core.evt.addEvent", function($) {
    return function(sNode, sEventType, oFunc) {
        var oElement = $.E(sNode);
        if (oElement == null) {
            return false;
        }
        sEventType = sEventType || "click";
        if ((typeof oFunc).toLowerCase() != "function") {
            return;
        }
        if (oElement.addEventListener) {
            oElement.addEventListener(sEventType, oFunc, false);
        } else if (oElement.attachEvent) {
            oElement.attachEvent("on" + sEventType, oFunc);
        } else {
            oElement["on" + sEventType] = oFunc;
        }
        return true;
    };
});

    STK.register("core.arr.isArray", function($) {
    return function(o) {
        return Object.prototype.toString.call(o) === "[object Array]";
    };
});


STK.register("core.str.trim", function($) {
    return function(str) {
        if (typeof str !== "string") {
            throw "trim need a string as parameter";
        }
        var len = str.length;
        var s = 0;
        var reg = /(\u3000|\s|\t|\u00A0)/;
        while (s < len) {
            if (!reg.test(str.charAt(s))) {
                break;
            }
            s += 1;
        }
        while (len > s) {
            if (!reg.test(str.charAt(len - 1))) {
                break;
            }
            len -= 1;
        }
        return str.slice(s, len);
    };
});


STK.register("core.json.queryToJson", function($) {
    return function(QS, isDecode) {
        var _Qlist = $.core.str.trim(QS).split("&");
        var _json = {};
        var _fData = function(data) {
            if (isDecode) {
                return decodeURIComponent(data);
            } else {
                return data;
            }
        };
        for (var i = 0, len = _Qlist.length; i < len; i++) {
            if (_Qlist[i]) {
                var _hsh = _Qlist[i].split("=");
                var _key = _hsh[0];
                var _value = _hsh[1];
                if (_hsh.length < 2) {
                    _value = _key;
                    _key = "$nullName";
                }
                if (!_json[_key]) {
                    _json[_key] = _fData(_value);
                } else {
                    if ($.core.arr.isArray(_json[_key]) != true) {
                        _json[_key] = [ _json[_key] ];
                    }
                    _json[_key].push(_fData(_value));
                }
            }
        }
        return _json;
    };
});


STK.register("core.dom.isNode", function($) {
    return function(node) {
        return node != undefined && Boolean(node.nodeName) && Boolean(node.nodeType);
    };
});


 

STK.register("core.dom.contains", function($) {
    return function(parent, node) {
        if (parent === node) {
            return false;
        } else if (parent.compareDocumentPosition) {
            return (parent.compareDocumentPosition(node) & 16) === 16;
        } else if (parent.contains && node.nodeType === 1) {
            return parent.contains(node);
        } else {
            while (node = node.parentNode) {
                if (parent === node) {
                    return true;
                }
            }
        }
        return false;
    };
});


 

STK.register("core.evt.removeEvent", function($) {
    return function(el, evType, func, useCapture) {
        var _el = $.E(el);
        if (_el == null) {
            return false;
        }
        if (typeof func != "function") {
            return false;
        }
        if (_el.removeEventListener) {
            _el.removeEventListener(evType, func, useCapture);
        } else if (_el.detachEvent) {
            _el.detachEvent("on" + evType, func);
        } else {
            _el["on" + evType] = null;
        }
        return true;
    };
});


 

STK.register("core.evt.getEvent", function($) {
    return function() {
        if ($.IE) {
            return window.event;
        } else {
            if (window.event) {
                return window.event;
            }
            var o = arguments.callee.caller;
            var e;
            var n = 0;
            while (o != null && n < 40) {
                e = o.arguments[0];
                if (e && (e.constructor == Event || e.constructor == MouseEvent || e.constructor == KeyboardEvent)) {
                    return e;
                }
                n++;
                o = o.caller;
            }
            return e;
        }
    };
});


STK.register("core.evt.fixEvent", function($) {
    return function(e) {
        e = e || $.core.evt.getEvent();
        if (!e.target) {
            e.target = e.srcElement;
            e.pageX = e.x;
            e.pageY = e.y;
        }
        if (typeof e.layerX == "undefined") e.layerX = e.offsetX;
        if (typeof e.layerY == "undefined") e.layerY = e.offsetY;
        return e;
    };
});


 

STK.register("core.obj.isEmpty", function($) {
    return function(o, isprototype) {
        var ret = true;
        for (var k in o) {
            if (isprototype) {
                ret = false;
                break;
            } else {
                if (o.hasOwnProperty(k)) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    };
});


STK.register("core.func.empty", function() {
    return function() {};
});


STK.register("core.evt.delegatedEvent", function($) {
    var checkContains = function(list, el) {
        for (var i = 0, len = list.length; i < len; i += 1) {
            if ($.core.dom.contains(list[i], el)) {
                return true;
            }
        }
        return false;
    };
    return function(actEl, expEls) {
        if (!$.core.dom.isNode(actEl)) {
            throw "core.evt.delegatedEvent need an Element as first Parameter";
        }
        if (!expEls) {
            expEls = [];
        }
        if ($.core.arr.isArray(expEls)) {
            expEls = [ expEls ];
        }
        var evtList = {};
        var bindEvent = function(e) {
            var evt = $.core.evt.fixEvent(e);
            var el = evt.target;
            var type = e.type;
            var changeTarget = function() {
                var path, lis, tg;
                path = el.getAttribute("action-target");
                if (path) {
                    lis = $.core.dom.sizzle(path, actEl);
                    if (lis.length) {
                        tg = evt.target = lis[0];
                    }
                }
                changeTarget = $.core.func.empty;
                return tg;
            };
            var checkBuble = function() {
                var tg = changeTarget() || el;
                if (evtList[type] && evtList[type][actionType]) {
                    return evtList[type][actionType]({
                        evt: evt,
                        el: tg,
                        box: actEl,
                        data: $.core.json.queryToJson(tg.getAttribute("action-data") || "")
                    });
                } else {
                    return true;
                }
            };
            if (checkContains(expEls, el)) {
                return false;
            } else if (!$.core.dom.contains(actEl, el)) {
                return false;
            } else {
                var actionType = null;
                while (el && el !== actEl) {
                    actionType = el.getAttribute("action-type");
                    if (actionType && checkBuble() === false) {
                        break;
                    }
                    el = el.parentNode;
                }
            }
        };
        var that = {};
        that.add = function(funcName, evtType, process) {
            if (!evtList[evtType]) {
                evtList[evtType] = {};
                $.core.evt.addEvent(actEl, evtType, bindEvent);
            }
            var ns = evtList[evtType];
            ns[funcName] = process;
        };
        that.remove = function(funcName, evtType) {
            if (evtList[evtType]) {
                delete evtList[evtType][funcName];
                if ($.core.obj.isEmpty(evtList[evtType])) {
                    delete evtList[evtType];
                    $.core.evt.removeEvent(actEl, evtType, bindEvent);
                }
            }
        };
        that.pushExcept = function(el) {
            expEls.push(el);
        };
        that.removeExcept = function(el) {
            if (!el) {
                expEls = [];
            } else {
                for (var i = 0, len = expEls.length; i < len; i += 1) {
                    if (expEls[i] === el) {
                        expEls.splice(i, 1);
                    }
                }
            }
        };
        that.clearExcept = function(el) {
            expEls = [];
        };
        that.destroy = function() {
            for (k in evtList) {
                for (l in evtList[k]) {
                    delete evtList[k][l];
                }
                delete evtList[k];
                $.core.evt.removeEvent(actEl, k, bindEvent);
            }
        };
        return that;
    };
});

     

STK.register("core.evt.preventDefault", function($) {
    return function(e) {
        var ev = e ? e : $.core.evt.getEvent();
        if ($.IE) {
            ev.returnValue = false;
        } else {
            ev.preventDefault();
        }
    };
});

     
     

 

STK.register("core.dom.removeNode", function($) {
    return function(node) {
        node = $.E(node) || node;
        try {
            node.parentNode.removeChild(node);
        } catch (e) {}
    };
});


STK.register("core.util.getUniqueKey", function($) {
    var _loadTime = (new Date).getTime().toString(), _i = 1;
    return function() {
        return _loadTime + _i++;
    };
});


 

 

STK.register("core.str.parseURL", function($) {
    return function(url) {
        var parse_url = /^(?:([A-Za-z]+):(\/{0,3}))?([0-9.\-A-Za-z]+\.[0-9A-Za-z]+)?(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
        var names = [ "url", "scheme", "slash", "host", "port", "path", "query", "hash" ];
        var results = parse_url.exec(url);
        var that = {};
        for (var i = 0, len = names.length; i < len; i += 1) {
            that[names[i]] = results[i] || "";
        }
        return that;
    };
});


 

 

STK.register("core.json.jsonToQuery", function($) {
    var _fdata = function(data, isEncode) {
        data = data == null ? "" : data;
        data = $.core.str.trim(data.toString());
        if (isEncode) {
            return encodeURIComponent(data);
        } else {
            return data;
        }
    };
    return function(JSON, isEncode) {
        var _Qstring = [];
        if (typeof JSON == "object") {
            for (var k in JSON) {
                if (k === "$nullName") {
                    _Qstring = _Qstring.concat(JSON[k]);
                    continue;
                }
                if (JSON[k] instanceof Array) {
                    for (var i = 0, len = JSON[k].length; i < len; i++) {
                        _Qstring.push(k + "=" + _fdata(JSON[k][i], isEncode));
                    }
                } else {
                    if (typeof JSON[k] != "function") {
                        _Qstring.push(k + "=" + _fdata(JSON[k], isEncode));
                    }
                }
            }
        }
        if (_Qstring.length) {
            return _Qstring.join("&");
        } else {
            return "";
        }
    };
});


STK.register("core.util.URL", function($) {
    return function(sURL, args) {
        var opts = $.core.obj.parseParam({
            isEncodeQuery: false,
            isEncodeHash: false
        }, args || {});
        var that = {};
        var url_json = $.core.str.parseURL(sURL);
        var query_json = $.core.json.queryToJson(url_json.query);
        var hash_json = $.core.json.queryToJson(url_json.hash);
        that.setParam = function(sKey, sValue) {
            query_json[sKey] = sValue;
            return this;
        };
        that.getParam = function(sKey) {
            return query_json[sKey];
        };
        that.setParams = function(oJson) {
            for (var key in oJson) {
                that.setParam(key, oJson[key]);
            }
            return this;
        };
        that.setHash = function(sKey, sValue) {
            hash_json[sKey] = sValue;
            return this;
        };
        that.getHash = function(sKey) {
            return hash_json[sKey];
        };
        that.valueOf = that.toString = function() {
            var url = [];
            var query = $.core.json.jsonToQuery(query_json, opts.isEncodeQuery);
            var hash = $.core.json.jsonToQuery(hash_json, opts.isEncodeQuery);
            if (url_json.scheme != "") {
                url.push(url_json.scheme + ":");
                url.push(url_json.slash);
            }
            if (url_json.host != "") {
                url.push(url_json.host);
                if (url_json.port != "") {
                    url.push(":");
                    url.push(url_json.port);
                }
            }
            url.push("/");
            url.push(url_json.path);
            if (query != "") {
                url.push("?" + query);
            }
            if (hash != "") {
                url.push("#" + hash);
            }
            return url.join("");
        };
        return that;
    };
});


STK.register("core.io.scriptLoader", function($) {
    var entityList = {};
    var default_opts = {
        url: "",
        charset: "UTF-8",
        timeout: 30 * 1e3,
        args: {},
        onComplete: $.core.func.empty,
        onTimeout: null,
        isEncode: false,
        uniqueID: null
    };
    return function(oOpts) {
        var js, requestTimeout;
        var opts = $.core.obj.parseParam(default_opts, oOpts);
        if (opts.url == "") {
            throw "scriptLoader: url is null";
        }
        var uniqueID = opts.uniqueID || $.core.util.getUniqueKey();
        js = entityList[uniqueID];
        if (js != null && $.IE != true) {
            $.core.dom.removeNode(js);
            js = null;
        }
        if (js == null) {
            js = entityList[uniqueID] = $.C("script");
        }
        js.charset = opts.charset;
        js.id = "scriptRequest_script_" + uniqueID;
        js.type = "text/javascript";
        if (opts.onComplete != null) {
            if ($.IE) {
                js["onreadystatechange"] = function() {
                    if (js.readyState.toLowerCase() == "loaded" || js.readyState.toLowerCase() == "complete") {
                        try {
                            clearTimeout(requestTimeout);
                            document.getElementsByTagName("head")[0].removeChild(js);
                            js["onreadystatechange"] = null;
                        } catch (exp) {}
                        opts.onComplete();
                    }
                };
            } else {
                js["onload"] = function() {
                    try {
                        clearTimeout(requestTimeout);
                        $.core.dom.removeNode(js);
                    } catch (exp) {}
                    opts.onComplete();
                };
            }
        }
        js.src = STK.core.util.URL(opts.url, {
            isEncodeQuery: opts["isEncode"]
        }).setParams(opts.args);
        document.getElementsByTagName("head")[0].appendChild(js);
        if (opts.timeout > 0 && opts.onTimeout != null) {
            requestTimeout = setTimeout(function() {
                try {
                    document.getElementsByTagName("head")[0].removeChild(js);
                } catch (exp) {}
                opts.onTimeout();
            }, opts.timeout);
        }
        return js;
    };
});


 

 

 

 

STK.register("core.io.jsonp", function($) {
    return function(oOpts) {
        var opts = $.core.obj.parseParam({
            url: "",
            charset: "UTF-8",
            timeout: 30 * 1e3,
            args: {},
            onComplete: null,
            onTimeout: null,
            responseName: null,
            isEncode: false,
            varkey: "callback"
        }, oOpts);
        var funcStatus = -1;
        var uniqueID = opts.responseName || "STK_" + $.core.util.getUniqueKey();
        opts.args[opts.varkey] = uniqueID;
        var completeFunc = opts.onComplete;
        var timeoutFunc = opts.onTimeout;
        window[uniqueID] = function(oResult) {
            if (funcStatus != 2 && completeFunc != null) {
                funcStatus = 1;
                completeFunc(oResult);
            }
        };
        opts.onComplete = null;
        opts.onTimeout = function() {
            if (funcStatus != 1 && timeoutFunc != null) {
                funcStatus = 2;
                timeoutFunc();
            }
        };
        return $.core.io.scriptLoader(opts);
    };
});


STK.register("common.login", function($) {
    var trim = $.core.str.trim;
    var that = {};
    window.sinaSSOConfig = {
        entry: "openapi",
        domain: "weibo.com",
        customLoginCallBack: function() {},
        customLogoutCallBack: function() {},
        useTicket: true
    };
    var loadSSO = function(callback) {
        if (typeof window.sinaSSOController != "undefined") {
            callback();
        } else {
            $.core.io.scriptLoader({
                url: "/oauth2/js/sso/ssologin.js",
                onComplete: function() {
                    setTimeout(callback, 100);
                }
            });
        }
    };
    that.doLogin = function(param) {
        var conf = $.core.obj.parseParam({
            userId: "",
            passWd: "",
            door: "",
            vsnval: "",
            remTime: 0,
            callback: function() {}
        }, param);
        var userId = trim(conf.userId), passWd = trim(conf.passWd);
        var _login = function() {
            sinaSSOController.customLoginCallBack = conf["callback"];
            sinaSSOController.loginExtraQuery.ct = 1800;
            sinaSSOController.crossDomain = false;
            sinaSSOController.loginExtraQuery["s"] = 1;
            sinaSSOController.loginExtraQuery["vsnf"] = 1;
            sinaSSOController.loginExtraQuery["vsnval"] = conf["vsnval"];
            sinaSSOController.loginExtraQuery["door"] = conf["door"];
            sinaSSOController.login(userId, passWd, conf["remTime"]);
        };
        loadSSO(_login);
    };
    that.doLogout = function() {
        if (document.location.host.indexOf("weibo.cn") != -1) {
            document.location.href = "http://api.weibo.cn/interface/f/login/logout.php?backUrl=" + encodeURIComponent(document.location.href);
            return;
        }
        var _logout = function() {
            sinaSSOController.customLogoutCallBack = function() {
                location.reload();
            };
            sinaSSOController.logout();
        };
        loadSSO(_logout);
    };
    return that;
});

     

 

STK.register("common.resize", function() {
    var defaultConfig = {
        width: 750 + 20,
        height: 450 + 180
    };
    function setSize(conf) {
        if (!/\((iPhone|iPad|iPod)/i.test(navigator.userAgent)) {
            (function(w, d) {
                var dw, dh, de = d.documentElement;
                dw = de && de.clientWidth ? de.clientWidth : d.body.clientWidth;
                dh = de && de.clientHeight ? de.clientHeight : d.body.clientHeight;
                try {
                    if (dw < conf["width"] || dh < conf["height"]) {
                        w.resizeTo(conf["width"], conf["height"]);
                    }
                    var resizeList = "js,popup";
                    var display = $.core.json.queryToJson(document.location.search.substr(1)).display || 0;
                    if (resizeList.indexOf(display) == -1) return;
                    var left = (w.screen.width - conf["width"]) / 2, top = (w.screen.height - conf["height"]) / 2;
                    w.moveTo(left, top);
                } catch (e) {}
            })(window, document);
        }
    }
    return function(opts) {
        var conf = $.core.obj.parseParam(defaultConfig, opts);
        setSize(conf);
    };
});

     

 

 

STK.register("core.dom.uniqueID", function($) {
    return function(node) {
        return node && (node.uniqueID || (node.uniqueID = $.core.util.getUniqueKey()));
    };
});


STK.register("core.obj.beget", function($) {
    var F = function() {};
    return function(o) {
        F.prototype = o;
        return new F;
    };
});


STK.register("core.dom.setStyle", function($) {
    return function(node, property, val) {
        if ($.IE) {
            switch (property) {
              case "opacity":
                node.style.filter = "alpha(opacity=" + val * 100 + ")";
                if (!node.currentStyle || !node.currentStyle.hasLayout) {
                    node.style.zoom = 1;
                }
                break;
              case "float":
                property = "styleFloat";
              default:
                node.style[property] = val;
            }
        } else {
            if (property == "float") {
                property = "cssFloat";
            }
            node.style[property] = val;
        }
    };
});


STK.register("core.dom.insertAfter", function($) {
    return function(node, target) {
        var parent = target.parentNode;
        if (parent.lastChild == target) {
            parent.appendChild(node);
        } else {
            parent.insertBefore(node, target.nextSibling);
        }
    };
});


STK.register("core.dom.insertBefore", function($) {
    return function(node, target) {
        var parent = target.parentNode;
        parent.insertBefore(node, target);
    };
});


STK.register("core.dom.hasClassName", function($) {
    return function(node, className) {
        return (new RegExp("\\b" + className + "\\b")).test(node.className);
    };
});


STK.register("core.dom.addClassName", function($) {
    return function(node, className) {
        if (node.nodeType === 1) {
            if (!$.core.dom.hasClassName(node, className)) {
                node.className += " " + className;
            }
        }
    };
});


 

STK.register("core.dom.removeClassName", function($) {
    return function(node, className) {
        if (node.nodeType === 1) {
            if ($.core.dom.hasClassName(node, className)) {
                node.className = node.className.replace(new RegExp("\\b" + className + "\\b"), " ");
            }
        }
    };
});


STK.register("core.dom.trimNode", function($) {
    return function(node) {
        var cn = node.childNodes;
        for (var i = 0; i < cn.length; i++) {
            if (cn[i].nodeType == 3 || cn[i].nodeType == 8) node.removeChild(cn[i]);
        }
    };
});


 

 

 

STK.register("core.evt.fireEvent", function($) {
    return function(el, sEvent) {
        _el = $.E(el);
        if ($.IE) {
            _el.fireEvent("on" + sEvent);
        } else {
            var evt = document.createEvent("HTMLEvents");
            evt.initEvent(sEvent, true, true);
            _el.dispatchEvent(evt);
        }
    };
});


STK.register("core.dom.getStyle", function($) {
    return function(node, property) {
        if ($.IE) {
            switch (property) {
              case "opacity":
                var val = 100;
                try {
                    val = node.filters["DXImageTransform.Microsoft.Alpha"].opacity;
                } catch (e) {
                    try {
                        val = node.filters("alpha").opacity;
                    } catch (e) {}
                }
                return val / 100;
              case "float":
                property = "styleFloat";
              default:
                var value = node.currentStyle ? node.currentStyle[property] : null;
                return node.style[property] || value;
            }
        } else {
            if (property == "float") {
                property = "cssFloat";
            }
            try {
                var computed = document.defaultView.getComputedStyle(node, "");
            } catch (e) {}
            return node.style[property] || computed ? computed[property] : null;
        }
    };
});


 

 

STK.register("core.dom.setXY", function($) {
    return function(node, pos) {
        var pos_style = $.core.dom.getStyle(node, "position");
        if (pos_style == "static") {
            $.core.dom.setStyle(node, "position", "relative");
            pos_style = "relative";
        }
        var page_xy = $.core.dom.position(node);
        if (page_xy == false) {
            return;
        }
        var delta = {
            l: parseInt($.core.dom.getStyle(node, "left"), 10),
            t: parseInt($.core.dom.getStyle(node, "top"), 10)
        };
        if (isNaN(delta["l"])) {
            delta["l"] = pos_style == "relative" ? 0 : node.offsetLeft;
        }
        if (isNaN(delta["t"])) {
            delta["t"] = pos_style == "relative" ? 0 : node.offsetTop;
        }
        if (pos["l"] != null) {
            node.style.left = pos["l"] - page_xy["l"] + delta["l"] + "px";
        }
        if (pos["t"] != null) {
            node.style.top = pos["t"] - page_xy["t"] + delta["t"] + "px";
        }
    };
});


STK.register("core.str.encodeHTML", function($) {
    return function(str) {
        if (typeof str !== "string") {
            throw "encodeHTML need a string as parameter";
        }
        return str.replace(/\&/g, "&amp;").replace(/"/g, "&quot;").replace(/\</g, "&lt;").replace(/\>/g, "&gt;").replace(/\'/g, "&#39;").replace(/\u00A0/g, "&nbsp;").replace(/(\u0020|\u000B|\u2028|\u2029|\f)/g, "&#32;");
    };
});


STK.register("core.str.decodeHTML", function($) {
    return function(str) {
        if (typeof str !== "string") {
            throw "decodeHTML need a string as parameter";
        }
        return str.replace(/&quot;/g, '"').replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&#39/g, "'").replace(/&nbsp;/g, " ").replace(/&#32/g, " ").replace(/&amp;/g, "&");
    };
});


 

STK.register("core.dom.cascadeNode", function($) {
    return function(node) {
        var that = {};
        var display = node.style.display || "";
        display = display === "none" ? "" : display;
        var eventCache = [];
        that.setStyle = function(property, value) {
            $.core.dom.setStyle(node, property, value);
            if (property === "display") {
                display = value === "none" ? "" : value;
            }
            return that;
        };
        that.insertAfter = function(el) {
            $.core.dom.insertAfter(el, node);
            return that;
        };
        that.insertBefore = function(el) {
            $.core.dom.insertBefore(el, node);
            return that;
        };
        that.addClassName = function(cn) {
            $.core.dom.addClassName(node, cn);
            return that;
        };
        that.removeClassName = function(cn) {
            $.core.dom.removeClassName(node, cn);
            return that;
        };
        that.trimNode = function() {
            $.core.dom.trimNode(node);
            return that;
        };
        that.removeNode = function() {
            $.core.dom.removeNode(node);
            return that;
        };
        that.on = function(type, func) {
            for (var i = 0, len = eventCache.length; i < len; i += 1) {
                if (eventCache[i]["fn"] === func && eventCache[i]["type"] === type) {
                    return that;
                }
            }
            eventCache.push({
                fn: func,
                type: type
            });
            $.core.evt.addEvent(node, type, func);
            return that;
        };
        that.unon = function(type, func) {
            for (var i = 0, len = eventCache.length; i < len; i += 1) {
                if (eventCache[i]["fn"] === func && eventCache[i]["type"] === type) {
                    $.core.evt.removeEvent(node, func, type);
                    eventCache.splice(i, 1);
                    break;
                }
            }
            return that;
        };
        that.fire = function(type) {
            $.core.evt.fireEvent(type, node);
            return that;
        };
        that.appendChild = function(el) {
            node.appendChild(el);
            return that;
        };
        that.removeChild = function(el) {
            node.removeChild(el);
            return that;
        };
        that.toggle = function() {
            if (node.style.display === "none") {
                node.style.display = display;
            } else {
                node.style.display = "none";
            }
            return that;
        };
        that.show = function() {
            if (node.style.display === "none") {
                if (display === "none") {
                    node.style.display = "";
                } else {
                    node.style.display = display;
                }
            }
            return that;
        };
        that.hidd = function() {
            if (node.style.display !== "none") {
                node.style.display = "none";
            }
            return that;
        };
        that.hide = that.hidd;
        that.scrollTo = function(type, value) {
            if (type === "left") {
                node.scrollLeft = value;
            }
            if (type === "top") {
                node.scrollTop = value;
            }
            return that;
        };
        that.replaceChild = function(newNode, oldNode) {
            node.replaceChild(newNode, oldNode);
            return that;
        };
        that.position = function(args) {
            if (args !== undefined) {
                $.core.dom.setXY(node, args);
            }
            return $.core.dom.position(node);
        };
        that.setPosition = function(args) {
            if (args !== undefined) {
                $.core.dom.setXY(node, args);
            }
            return that;
        };
        that.getPosition = function(args) {
            return $.core.dom.position(node);
        };
        that.html = function(html) {
            if (html !== undefined) {
                node.innerHTML = html;
            }
            return node.innerHTML;
        };
        that.setHTML = function(html) {
            if (html !== undefined) {
                node.innerHTML = html;
            }
            return that;
        };
        that.getHTML = function() {
            return node.innerHTML;
        };
        that.text = function(text) {
            if (text !== undefined) {
                node.innerHTML = $.core.str.encodeHTML(text);
            }
            return $.core.str.decodeHTML(node.innerHTML);
        };
        that.ttext = that.text;
        that.setText = function(text) {
            if (text !== undefined) {
                node.innerHTML = $.core.str.encodeHTML(text);
            }
            return that;
        };
        that.getText = function() {
            return $.core.str.decodeHTML(node.innerHTML);
        };
        that.get = function(key) {
            if (key === "node") {
                return node;
            }
            return $.core.dom.getStyle(node, key);
        };
        that.getStyle = function(key) {
            return $.core.dom.getStyle(node, key);
        };
        that.getOriginNode = function() {
            return node;
        };
        that.destroy = function() {
            for (var i = 0, len = eventCache; i < len; i += 1) {
                $.core.evt.removeEvent(node, eventCache[i]["fn"], eventCache[i]["type"]);
            }
            display = null;
            eventCache = null;
            node = null;
        };
        return that;
    };
});


 

STK.register("core.evt.custEvent", function($) {
    var _custAttr = "__custEventKey__", _custKey = 1, _custCache = {}, _findObj = function(obj, type) {
        var _key = typeof obj == "number" ? obj : obj[_custAttr];
        return _key && _custCache[_key] && {
            obj: typeof type == "string" ? _custCache[_key][type] : _custCache[_key],
            key: _key
        };
    };
    return {
        define: function(obj, type) {
            if (obj && type) {
                var _key = typeof obj == "number" ? obj : obj[_custAttr] || (obj[_custAttr] = _custKey++), _cache = _custCache[_key] || (_custCache[_key] = {});
                type = [].concat(type);
                for (var i = 0; i < type.length; i++) {
                    _cache[type[i]] || (_cache[type[i]] = []);
                }
                return _key;
            }
        },
        undefine: function(obj, type) {
            if (obj) {
                var _key = typeof obj == "number" ? obj : obj[_custAttr];
                if (_key && _custCache[_key]) {
                    if (type) {
                        type = [].concat(type);
                        for (var i = 0; i < type.length; i++) {
                            if (type[i] in _custCache[_key]) delete _custCache[_key][type[i]];
                        }
                    } else {
                        delete _custCache[_key];
                    }
                }
            }
        },
        add: function(obj, type, fn, data) {
            if (obj && typeof type == "string" && fn) {
                var _cache = _findObj(obj, type);
                if (!_cache || !_cache.obj) {
                    throw "custEvent (" + type + ") is undefined !";
                }
                _cache.obj.push({
                    fn: fn,
                    data: data
                });
                return _cache.key;
            }
        },
        once: function(obj, type, fn, data) {
            if (obj && typeof type == "string" && fn) {
                var _cache = _findObj(obj, type);
                if (!_cache || !_cache.obj) {
                    throw "custEvent (" + type + ") is undefined !";
                }
                _cache.obj.push({
                    fn: fn,
                    data: data,
                    once: true
                });
                return _cache.key;
            }
        },
        remove: function(obj, type, fn) {
            if (obj) {
                var _cache = _findObj(obj, type), _obj, index;
                if (_cache && (_obj = _cache.obj)) {
                    if ($.core.arr.isArray(_obj)) {
                        if (fn) {
                            var i = 0;
                            while (_obj[i]) {
                                if (_obj[i].fn === fn) {
                                    break;
                                }
                                i++;
                            }
                            _obj.splice(i, 1);
                        } else {
                            _obj.splice(0, _obj.length);
                        }
                    } else {
                        for (var i in _obj) {
                            _obj[i] = [];
                        }
                    }
                    return _cache.key;
                }
            }
        },
        fire: function(obj, type, args) {
            if (obj && typeof type == "string") {
                var _cache = _findObj(obj, type), _obj;
                if (_cache && (_obj = _cache.obj)) {
                    if (!$.core.arr.isArray(args)) {
                        args = args != undefined ? [ args ] : [];
                    }
                    for (var i = _obj.length - 1; i > -1 && _obj[i]; i--) {
                        var fn = _obj[i].fn;
                        var isOnce = _obj[i].once;
                        if (fn && fn.apply) {
                            try {
                                fn.apply(obj, [ {
                                    type: type,
                                    data: _obj[i].data
                                } ].concat(args));
                                if (isOnce) {
                                    _obj.splice(i, 1);
                                }
                            } catch (e) {
                                $.log("[error][custEvent]" + e.message);
                            }
                        }
                    }
                    return _cache.key;
                }
            }
        },
        destroy: function() {
            _custCache = {};
            _custKey = 1;
        }
    };
});


 

 

STK.register("module.layer", function($) {
    var getSize = function(box) {
        var ret = {};
        if (box.style.display == "none") {
            box.style.visibility = "hidden";
            box.style.display = "";
            ret.w = box.offsetWidth;
            ret.h = box.offsetHeight;
            box.style.display = "none";
            box.style.visibility = "visible";
        } else {
            ret.w = box.offsetWidth;
            ret.h = box.offsetHeight;
        }
        return ret;
    };
    var getPosition = function(el, key) {
        key = key || "topleft";
        var posi = null;
        if (el.style.display == "none") {
            el.style.visibility = "hidden";
            el.style.display = "";
            posi = $.core.dom.position(el);
            el.style.display = "none";
            el.style.visibility = "visible";
        } else {
            posi = $.core.dom.position(el);
        }
        if (key !== "topleft") {
            var size = getSize(el);
            if (key === "topright") {
                posi["l"] = posi["l"] + size["w"];
            } else if (key === "bottomleft") {
                posi["t"] = posi["t"] + size["h"];
            } else if (key === "bottomright") {
                posi["l"] = posi["l"] + size["w"];
                posi["t"] = posi["t"] + size["h"];
            }
        }
        return posi;
    };
    return function(template) {
        var dom = $.core.dom.builder(template);
        var outer = dom.list["outer"][0], inner = dom.list["inner"][0];
        var uniqueID = $.core.dom.uniqueID(outer);
        var that = {};
        var custKey = $.core.evt.custEvent.define(that, "show");
        $.core.evt.custEvent.define(custKey, "hide");
        var sizeCache = null;
        that.show = function() {
            outer.style.display = "";
            $.core.evt.custEvent.fire(custKey, "show");
            return that;
        };
        that.hide = function() {
            outer.style.display = "none";
            $.core.evt.custEvent.fire(custKey, "hide");
            return that;
        };
        that.getPosition = function(key) {
            return getPosition(outer, key);
        };
        that.getSize = function(isFlash) {
            if (isFlash || !sizeCache) {
                sizeCache = getSize.apply(that, [ outer ]);
            }
            return sizeCache;
        };
        that.html = function(html) {
            if (html !== undefined) {
                inner.innerHTML = html;
            }
            return inner.innerHTML;
        };
        that.text = function(str) {
            if (text !== undefined) {
                inner.innerHTML = $.core.str.encodeHTML(str);
            }
            return $.core.str.decodeHTML(inner.innerHTML);
        };
        that.appendChild = function(node) {
            inner.appendChild(node);
            return that;
        };
        that.getUniqueID = function() {
            return uniqueID;
        };
        that.getOuter = function() {
            return outer;
        };
        that.getInner = function() {
            return inner;
        };
        that.getParentNode = function() {
            return outer.parentNode;
        };
        that.getDomList = function() {
            return dom.list;
        };
        that.getDomListByKey = function(key) {
            return dom.list[key];
        };
        that.getDom = function(key, index) {
            if (!dom.list[key]) {
                return false;
            }
            return dom.list[key][index || 0];
        };
        that.getCascadeDom = function(key, index) {
            if (!dom.list[key]) {
                return false;
            }
            return $.core.dom.cascadeNode(dom.list[key][index || 0]);
        };
        return that;
    };
});


 

 

STK.register("core.util.winSize", function($) {
    return function(_target) {
        var w, h;
        var target;
        if (_target) {
            target = _target.document;
        } else {
            target = document;
        }
        if (target.compatMode === "CSS1Compat") {
            w = target.documentElement["clientWidth"];
            h = target.documentElement["clientHeight"];
        } else if (self.innerHeight) {
            if (_target) {
                target = _target.self;
            } else {
                target = self;
            }
            w = target.innerWidth;
            h = target.innerHeight;
        } else if (target.documentElement && target.documentElement.clientHeight) {
            w = target.documentElement.clientWidth;
            h = target.documentElement.clientHeight;
        } else if (target.body) {
            w = target.body.clientWidth;
            h = target.body.clientHeight;
        }
        return {
            width: w,
            height: h
        };
    };
});


STK.register("ui.tip", function($) {
    var template = '<div style="position:absolute; width:200px; display:none;" class="tips WB_tips_yls" node-type="outer" >' + '<span class="WB_tipS_err"></span>' + '<span class="WB_sp_txt" node-type="inner"></span>' + '<span class="arr" node-type="arrow"></span>' + '<a class="close" node-type="close" href="javascript:void(0)">×</a>' + "</div>";
    return function(con, arrow) {
        var layer, box, content, that, arrowEl, close;
        var setPosition = function(pos) {
            box.style.top = pos["t"] + "px";
            box.style.left = pos["l"] + "px";
            return that;
        };
        var setMiddle = function() {
            var win = $.core.util.winSize();
            var dia = layer.getSize(true);
            box.style.top = $.core.util.scrollPos()["top"] + (win.height - dia.h) / 2 + "px";
            box.style.left = (win.width - dia.w) / 2 + "px";
            return that;
        };
        var setContent = function(cont) {
            if (typeof cont === "string") {
                content.innerHTML = cont;
            } else {
                content.appendChild(cont);
            }
            return that;
        };
        var setArrow = function(arrow, pos) {};
        var setArrowPos = function(arrow) {};
        var init = function() {
            layer = $.module.layer(template);
            box = layer.getOuter();
            content = layer.getDom("inner");
            arrowEl = layer.getDom("arrow");
            close = layer.getDom("close");
            $.core.evt.addEvent(close, "click", layer.hide);
            that = layer;
            con && setContent(con);
            document.body.appendChild(box);
        };
        init();
        that.setPosition = setPosition;
        that.setMiddle = setMiddle;
        that.setContent = setContent;
        that.setArrow = setArrow;
        return that;
    };
});

    var $ = STK, addEvent = $.core.evt.addEvent, trim = $.core.str.trim, sizzle = $.core.dom.sizzle;
    var tipText = "请用你的新浪微博帐号登录";
    var display = $.core.json.queryToJson(document.location.search.substr(1)).display || 0;
    var initJs = function(node) {
        var nodes, delegateEvt, tip, vdunBox, vcodeBox, currEle, checkVdun = false, checkCode = false;
        var subCss = [];
        var argsCheck = function() {
            if (!node) throw "node is not defined";
        };
        var parseDOM = function() {
            nodes = $.kit.dom.parseDOM($.core.dom.builder(node).list);
            vdunBox = nodes["vdunBox"];
            vcodeBox = nodes["validateBox"];
            if (display == "mobile") {
                subCss = [ "btnP", "btnI" ];
            } else {
                var css = nodes["submit"].className;
                cssArr = css.split(" ");
                subCss = [ css, cssArr[0] + "Ing " + cssArr[1] ];
            }
        };
        var bindDOM = function() {
            delegateEvt = $.core.evt.delegatedEvent(node);
            delegateEvt.add("submit", "click", loginFuns.submit);
            delegateEvt.add("logout", "click", loginFuns.logout);
            addEvent(nodes["cancel"], "click", loginFuns.cancel);
            if (nodes["userid"]) {
                addEvent(nodes["form"], "submit", loginFuns.submit);
                addEvent(nodes["userid"], "focus", loginFuns.toggleTip);
                addEvent(nodes["userid"], "blur", loginFuns.toggleTip);
                addEvent(nodes["userid"], "click", loginFuns.hideTip);
                addEvent(nodes["passwd"], "click", loginFuns.hideTip);
                addEvent(nodes["changeCode"], "click", loginFuns.pinCode);
                addEvent(window, "resize", loginFuns.rePosTip);
            }
        };
        var loginFuns = {
            submit: function() {
                var flag = $.E("officalFlag");
                $.E("withOfficalFlag").value = flag && flag.checked ? "1" : "0";
                if (nodes["userid"]) {
                    $.core.evt.preventDefault();
                    var userId = nodes["userid"].value;
                    var passWd = nodes["passwd"].value;
                    var vsnval = "", door = "";
                    if (userId == "" || userId == tipText) {
                        tip.setContent("请输入微博帐号");
                        tip.show();
                        return;
                    }
                    if (passWd == "") {
                        tip.setContent("请输入密码");
                        tip.show();
                        return;
                    }
                    if (checkVdun) {
                        vsnval = trim(nodes["vdun"].value);
                        if (vsnval == "" || vsnval.length < 6) {
                            tip.setContent("请输入微盾显示的动态密码").show();
                            return;
                        }
                    }
                    if (checkCode) {
                        door = trim(nodes["vcode"].value);
                        if (door == "") {
                            tip.setContent("请输入验证码").show();
                            return;
                        }
                    }
                    nodes["submit"].className = subCss[1];
                    $.common.login.doLogin({
                        userId: userId,
                        passWd: passWd,
                        vsnval: vsnval,
                        door: door,
                        callback: loginFuns.callback
                    });
                } else {
                    nodes["submit"].className = subCss[1];
                    $.E("action").value = "submit";
                    nodes["form"].submit();
                }
            },
            logout: function() {
                $.common.login.doLogout();
            },
            toggleTip: function(e) {
                var input = nodes["userid"];
                if (input.value == tipText && e.type == "focus") {
                    input.value = "";
                } else if (input.value == "" && e.type == "blur") {
                    input.value = tipText;
                }
            },
            hideTip: function() {
                tip && tip.hide();
            },
            rePosTip: function() {
                if (!currEle) return;
                tip.setPosition(loginFuns.getPos(currEle));
            },
            getPos: function(ele) {
                currEle = ele;
                var pos = $.core.dom.position(ele);
                pos.t -= ele.offsetHeight + 20;
                return pos;
            },
            pinCode: function(obj) {
                var codePic = sinaSSOController.getPinCodeUrl();
                nodes["pincode"].src = codePic;
                nodes["vcode"].value = "";
                vcodeBox.style.display = "";
                checkCode = true;
                obj.reason && tip.setContent(obj.reason).show();
            },
            showVdun: function(obj) {
                vdunBox.style.display = "";
                if (!checkVdun) {
                    obj.reason = "请输入微盾显示的动态密码";
                }
                checkVdun = true;
                nodes["vdun"].value = "";
                obj.reason && tip.setContent(obj.reason).show();
            },
            cancel: function() {
                $.E("action").value = "refused";
                nodes["userid"] && (nodes["userid"].value = "");
                nodes["form"].submit();
            },
            callback: function(obj) {
                if (!obj.result) {
                    switch (obj.errno) {
                      case "5024":
                      case "5025":
                        loginFuns.showVdun(obj);
                        break;
                      case "4049":
                      case "2070":
                        loginFuns.pinCode(obj);
                        break;
                      default:
                        tip.setContent(obj.reason).show();
                        break;
                    }
                    nodes["submit"].className = subCss[0];
                    return;
                }
                if (obj.ticket != "" && display != "mobile") {
                    $.E("ticket").value = obj.ticket;
                    nodes["passwd"].value = "";
                }
                $.E("action").value = "submit";
                nodes["form"].submit();
            }
        };
        var autoResize = function() {
            var resizeList = "default,js,popup";
            if (display == 0 || resizeList.indexOf(display) != -1) {
                $.common.resize();
            }
        };
        var initPlugins = function() {
            if (nodes["userid"]) {
                tip = $.ui.tip();
                tip.setPosition(loginFuns.getPos(nodes["userid"]));
                if (typeof $CONFIG != "undefined" && $CONFIG.$errorMsg) {
                    tip.setContent($CONFIG.$errorMsg).show();
                }
            }
            autoResize();
        };
        var init = function() {
            argsCheck();
            parseDOM();
            bindDOM();
            initPlugins();
        };
        init();
    };
    initJs($.E("outer"));
})();
