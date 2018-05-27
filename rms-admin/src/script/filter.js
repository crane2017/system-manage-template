// 日期格式化
const formatDate = (value, format) => {
  if (value != null && value !== '') {
    value = new Date(value);
    var o = {
      'M+': value.getMonth() + 1, // month
      'd+': value.getDate(), // day
      'h+': value.getHours(), // hour
      'm+': value.getMinutes(), // minute
      's+': value.getSeconds(), // second
      'q+': Math.floor((value.getMonth() + 3) / 3), // quarter
      'S': value.getMilliseconds() // millisecond
    };
    if (/(y+)/.test(format)) {
      format = format.replace(RegExp.$1,
        (value.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
      if (new RegExp('(' + k + ')').test(format)) {
        format = format.replace(RegExp.$1,
          RegExp.$1.length === 1 ? o[k]
            : ('00' + o[k]).substr(('' + o[k]).length));
      }
    }
    return format;
  } else {
    return '';
  }
};

const formatRole = (value, options) => {
  for (var index in options) {
    if (value === options[index].admin_role_pkid) {
      return options[index].admin_role_name;
    }
  }
  return value;
};

export {
  formatDate,
  formatRole
};
