function Selector() {
    this._value = {};
    this.toggle = function(locationStr) {
        _toggle(this._value, locationStr);
    };
    this.getQuery = function() {
        return _recurseSelectorString(this._value);
    };
}

_toggle = function(selectorPtr, locationStr) {
    var location = _parseLocationStr(locationStr);
    for(var i = 0 ; i < location.length ; ++i) {
        if (i < (location.length - 1)) { // Navigating down
            selectorPtr = selectorPtr[location[i]];
            // Can add exception here if selectionPtr is null
        }
        else if (selectorPtr[location[i]]) { // On, turn it off
            delete selectorPtr[location[i]];
        }
        else { // Off, turn it on
            selectorPtr[location[i]] = {};
        }
    }
};

_recurseSelectorString = function(selectorPtr) {
    if (!_hasElements(selectorPtr)) {
        return '';
    }
    else {
        var selectorStr = ':(';
        var isFirstNode = true;
        for (node in selectorPtr) {
            if (!isFirstNode) {
                selectorStr += ',';
            }
            else {
                isFirstNode = false;
            }
            selectorStr += node;
            selectorStr += _recurseSelectorString(selectorPtr[node]);
        }
        selectorStr += ')';
        return selectorStr;
    }
}

_parseLocationStr = function(locationStr) {
    return locationStr.split('.');
}

_hasElements = function(assocArray) {
    for(x in assocArray) { return true; }
    return false;
}
