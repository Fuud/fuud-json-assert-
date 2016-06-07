package org.fuud.json.asserts.impl.model;

import org.fuud.json.asserts.impl.parse.CharAndPosition;
import org.fuud.json.asserts.impl.parse.JsonParseException;
import org.fuud.json.asserts.impl.parse.Source;
import org.fuud.json.asserts.impl.util.Utils;

import java.io.IOException;
import java.util.Objects;

public class ObjectPropertyNode {
    private final String name;
    private final Node value;

    public ObjectPropertyNode(String name, Node value) {
        this.name = Objects.requireNonNull(name);
        this.value = Objects.requireNonNull(value);
    }

    public String getName() {
        return name;
    }

    public Node getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name +
                ":" +
                Utils.addIdentExceptFirstLine.apply(
                        value.toString()
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectPropertyNode)) return false;

        ObjectPropertyNode that = (ObjectPropertyNode) o;

        if (!name.equals(that.name)) return false;
        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    public static ObjectPropertyNode parse(Source source) throws IOException {
        final StringNode nameNode = StringNode.parse(source);
        final CharAndPosition delimiter = source.readNextNonSpaceChar();
        if (delimiter.getCharacter() != ':') {
            throw new JsonParseException(":", delimiter);
        }

        final ValueNode valueNode = ValueNode.parse(source);
        return new ObjectPropertyNode(nameNode.getValue(), valueNode);
    }
}
