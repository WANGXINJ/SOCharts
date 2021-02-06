/**
 * 
 */


var echartUtil = function() {
  // xj
  var LABEL_REG = /\{(.+?)(%.+?)?\}/g;

  // xj
  function buildAliasPatterns(tpl) {
    var patterns = new Map();
    var aliasPattern;
    while ((aliasPattern = LABEL_REG.exec(tpl)) !== null) {
      var alias = aliasPattern[1];
      var pattern = aliasPattern[2];
      if (alias && pattern) {
        patterns.set(alias, aliasPattern);
      }
    }

    return patterns;
  }

  // xj
  function wrapPatternAlias(aliasPattern, alias, seriesIdx) {
    var pattern = '';
    if (aliasPattern) {
      pattern = aliasPatter[2];
    }

    return '{' + alias + (seriesIdx == null ? '' : seriesIdx) + pattern + '}';
  };

  // xj
  function formatValueByPattern(val, pattern) {
    if (pattern.startsWith('%n')) {
      val = formatNumberByPattern(val, pattern);
    } else if (pattern.startsWith('%p')) {
      val = formatNumberByPattern(val * 100, pattern) + '%';
    } else if (pattern.startsWith('%d')) {
      val = formatDateByPattern(val, pattern);
    }

    return val;
  }

  // xj
  function formatNumberByPattern(num, pattern) {
    var precision = getPatternPrecision(pattern);
    if (precision) {
      num = num.toFixed(precision);
    }
 
    var kIndex = pattern.indexOf(',');
    if (kIndex) {
      num = addCommas(num);
    }

    return num;
  }

  // xj
  function formatDateByPattern(date, pattern) {
    var format = pattern.replace('%d', '');
    if (format.chartAt(0) == '\'' && format.chartAt(format.length - 1) == '\'') {
      format = format.substring(1, format.length - 1);
    }

    return formatTime(format, date, false);
  }
  
  // xj
  function getPatternPrecision(pattern) {
    var dotIndex = pattern.indexOf('.');
    if (dotIndex == -1) {
      return null;
    }

    var precisionStr = pattern.substring(dotIndex + 1);
    for (i = 0; i < precisionStr.length; i++) {
      var charAt = precisionStr.charAt(i);
      if (!(charAt >= '0' && charAt <= '9'))
        break;
    }
    
    precisionStr = precisionStr.substring(0, i);
    return parseInt(precisionStr);
  }
}