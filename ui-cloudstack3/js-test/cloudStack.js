(function($, testData) {
  $.extend(window.cloudStack, testData, {
    home: 'dashboard',

    sections: {
      /**
       * Dashboard
       */
      dashboard: {},
      instances: {},
      storage: {},
      network: {},
      templates: {},
      accounts: {},
      domains: {},
      events: {},
      system: {},
      configuration: {}
    }
  });

  $(function() {
    $('#cloudStack3-container').cloudStack(cloudStack);
  });
})(jQuery, testData);
