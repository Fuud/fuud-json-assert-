package org.fuud.json.asserts.impl.model

import org.fuud.json.asserts.impl.diff.ComparatorCreator
import org.fuud.json.asserts.impl.parse.Context
import org.fuud.json.asserts.impl.parse.Source
import org.intellij.lang.annotations.Language
import spock.lang.Specification


class ArrayNodeTest extends Specification {
    def "test valid array #json"(String json, ArrayNode expected) {
        when:
            ArrayNode parsed = ValueNode.parse(new Source(json), new Context())
        then:
            parsed == expected
        where:
            json                                    | expected
            '[]'                                    | new ArrayNode([])
            '/*this is \n multiline \n comment*/[]' | new ArrayNode([])
            '// this is one line comment \n []'     | new ArrayNode([])
            '["a"]'                                 | new ArrayNode([new StringNode("a")])
            '[{"a":"b"}, ["c", "d"]]'               | new ArrayNode([new ObjectNode([new ObjectPropertyNode("a", new StringNode("b"))]), new ArrayNode([new StringNode("c"), new StringNode("d")])])

    }
}
