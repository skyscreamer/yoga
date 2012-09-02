function Selector() {
    this._value = {};
    this.toggle = function(locationStr) {
        _toggle(this._value, locationStr, false, false);
    };
    this.toggle_on = function(locationStr) {
        _toggle(this._value, locationStr, true, false);
    };
    this.toggle_off = function(locationStr) {
        _toggle(this._value, locationStr, false, true);
    };
    this.getQuery = function() {
        return _recurseSelectorString(this._value);
    };
}

_toggle = function(selectorPtr, locationStr, on_only, off_only) {
    var location = _parseLocationStr(locationStr);
    for(var i = 0 ; i < location.length ; ++i) {
        if (i < (location.length - 1)) { // Navigating down
            selectorPtr = selectorPtr[location[i]];
            // Can add exception here if selectionPtr is null
        }
        else if (selectorPtr[location[i]]) { // On, turn it off
            if (!on_only) {
                delete selectorPtr[location[i]];
            }
        }
        else { // Off, turn it on
            if (!off_only) {
                selectorPtr[location[i]] = {};
            }
        }
    }
};

_recurseSelectorString = function(selectorPtr) {
    if (!_hasElements(selectorPtr)) {
        return '';
    }
    else {
        var selectorStr = '';
        var isFirstNode = true;
        for (node in selectorPtr) {
            if (!isFirstNode) {
                selectorStr += ',';
            }
            else {
                isFirstNode = false;
            }
            selectorStr += node;
            if (_hasElements(selectorPtr[node])) {
                selectorStr += '(' + _recurseSelectorString(selectorPtr[node]) + ')';
            }
        }
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
