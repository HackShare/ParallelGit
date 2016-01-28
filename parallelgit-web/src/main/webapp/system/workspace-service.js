app.service('WorkspaceService', function(ConnectionService) {

  function guid() {
    function _p8(s) {
      var p = (Math.random().toString(16) + "000000000").substr(2,8);
      return s ? "-" + p.substr(0,4) + "-" + p.substr(4,4) : p ;
    }
    return _p8() + _p8(true) + _p8(true) + _p8();
  }

  this.request = function(type, target) {
    var rid = guid();
    ConnectionService.send('request', {
      rid: rid,
      target: target,
      type: type
    });
    return rid;
  };

  this.update = function(type, target, value) {
    ConnectionService.send('update', {
      target: target,
      type: type,
      value: value
    })
  }

});