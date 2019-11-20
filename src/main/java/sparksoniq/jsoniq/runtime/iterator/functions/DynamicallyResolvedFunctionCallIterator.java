/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Authors: Stefan Irimescu, Can Berker Cikis
 *
 */

package sparksoniq.jsoniq.runtime.iterator.functions;

import org.apache.spark.api.java.JavaRDD;
import org.rumbledb.api.Item;
import sparksoniq.exceptions.IteratorFlowException;
import sparksoniq.jsoniq.item.FunctionItem;
import sparksoniq.jsoniq.runtime.iterator.HybridRuntimeIterator;
import sparksoniq.jsoniq.runtime.iterator.RuntimeIterator;
import sparksoniq.jsoniq.runtime.iterator.functions.base.FunctionIdentifier;
import sparksoniq.jsoniq.runtime.iterator.functions.base.Functions;
import sparksoniq.jsoniq.runtime.metadata.IteratorMetadata;
import sparksoniq.semantics.DynamicContext;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DynamicallyResolvedFunctionCallIterator extends HybridRuntimeIterator {

    private static final long serialVersionUID = 1L;
    // parametrized fields
    private FunctionIdentifier _functionIdentifier;
    private List<RuntimeIterator> _functionArguments;

    // calculated fields
    private FunctionItem _functionItem;
    private RuntimeIterator _functionCallIterator;
    private Item _nextResult;


    public DynamicallyResolvedFunctionCallIterator(
            FunctionIdentifier functionIdentifier,
            List<RuntimeIterator> functionArguments,
            IteratorMetadata iteratorMetadata
    ) {
        super(null, iteratorMetadata);
        _functionIdentifier = functionIdentifier;
        _functionArguments = functionArguments;

    }

    @Override
    public void openLocal() {
        _functionCallIterator = Functions.getUserDefinedFunctionIterator(
            _functionIdentifier,
            getMetadata(),
            _functionArguments
        );
        _functionCallIterator.open(_currentDynamicContext);
        setNextResult();
    }

    @Override
    public Item nextLocal() {
        if (this._hasNext) {
            Item result = _nextResult;
            setNextResult();
            return result;
        }
        throw new IteratorFlowException(
                RuntimeIterator.FLOW_EXCEPTION_MESSAGE + " in " + _functionIdentifier.getName() + "  function",
                getMetadata()
        );
    }

    @Override
    protected boolean hasNextLocal() {
        return _hasNext;
    }

    @Override
    protected void resetLocal(DynamicContext context) {
        _functionCallIterator.reset(_currentDynamicContext);
        setNextResult();
    }

    @Override
    protected void closeLocal() {
        // ensure that recursive function calls terminate gracefully
        // the function call in the body of the deepest recursion call is never visited, never opened and never closed
        if (this.isOpen()) {
            _functionCallIterator.close();
        }
    }

    public void setNextResult() {
        _nextResult = null;
        if (_functionCallIterator.hasNext()) {
            _nextResult = _functionCallIterator.next();
        }

        if (_nextResult == null) {
            this._hasNext = false;
            _functionCallIterator.close();
        } else {
            this._hasNext = true;
        }
    }

    @Override
    public JavaRDD<Item> getRDDAux(DynamicContext dynamicContext) {
        return _functionCallIterator.getRDD(_currentDynamicContext);
    }

    @Override
    public boolean initIsRDD() {
        _functionCallIterator = Functions.getUserDefinedFunctionIterator(
            _functionIdentifier,
            getMetadata(),
            _functionArguments
        );
        return _functionCallIterator.isRDD();
    }

    public Map<String, DynamicContext.VariableDependency> getVariableDependencies() {
        Map<String, DynamicContext.VariableDependency> result =
            new TreeMap<String, DynamicContext.VariableDependency>();
        result.putAll(super.getVariableDependencies());
        for (RuntimeIterator iterator : _functionArguments) {
            if (iterator == null)
                continue;
            result.putAll(iterator.getVariableDependencies());
        }
        return result;
    }

}
