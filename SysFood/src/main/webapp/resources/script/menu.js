(function($) {
	$(document).ready(
			function() {

				$('#cssmenu a')
						.each(
								function() {
									var $this = $(this);
									var hrefValue = $this.attr('href');

									if (hrefValue != null && hrefValue != "") {

										var page = document.location.pathname;

										page = page.split("/");
										page = page[page.length - 1];

										if (hrefValue.includes(page)
												&& hrefValue != null
												&& hrefValue != '') {
											$this.addClass('active');
											$this.parents('li.has-sub')
													.addClass('open');
											$this.parents('ul').slideDown(200)
													.show();
										}
									}
								});

				$('#cssmenu li.has-sub>a').on('click', function() {
					$(this).removeAttr('href');
					var element = $(this).parent('li');

					if (element.hasClass('open')) {
						element.removeClass('open');
						element.find('li').removeClass('open');
						element.find('ul').slideUp(200);
					} else {
						element.addClass('open');
						element.children('ul').slideDown(200);
						element.siblings('li').children('ul').slideUp(200);
						element.siblings('li').removeClass('open');
						element.siblings('li').find('li').removeClass('open');
						element.siblings('li').find('ul').slideUp(200);
					}
				});
			});
})(jQuery);
