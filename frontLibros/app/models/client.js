import DS from 'ember-data';

export default DS.Model.extend({

  idUsuario: DS.attr('Number'),
  password: DS.attr('String')

});
