/* ==========================================================================
   Formulário de lead — máscara, validação e envio
   ========================================================================== */
(function () {
  'use strict';

  /* Endpoint da API (Spring Boot).
     Em produção, trocar por uma URL relativa ou pelo domínio real. */
  var API_BASE_URL = 'https://inovacatalogo.onrender.com';
  var API_URL = API_BASE_URL + '/lead';

  var form = document.getElementById('leadForm');
  if (!form) return;

  var submitBtn = document.getElementById('submitBtn');
  var feedback = document.getElementById('formFeedback');
  var telefone = document.getElementById('telefone');

  /* ------------------------------------------------------------------------
     Máscara de telefone: (XX) XXXXX-XXXX  /  (XX) XXXX-XXXX
     ------------------------------------------------------------------------ */
  function maskPhone(value) {
    var digits = value.replace(/\D/g, '').slice(0, 11);

    if (digits.length === 0) return '';
    if (digits.length <= 2) return '(' + digits;
    if (digits.length <= 6) return '(' + digits.slice(0, 2) + ') ' + digits.slice(2);
    if (digits.length <= 10) {
      return '(' + digits.slice(0, 2) + ') ' + digits.slice(2, 6) + '-' + digits.slice(6);
    }
    return '(' + digits.slice(0, 2) + ') ' + digits.slice(2, 7) + '-' + digits.slice(7);
  }

  telefone.addEventListener('input', function () {
    telefone.value = maskPhone(telefone.value);
  });

  /* ------------------------------------------------------------------------
     Validação
     ------------------------------------------------------------------------ */
  var RULES = {
    nome: function (v) {
      if (!v.trim()) return 'Informe seu nome.';
      if (v.trim().length < 3) return 'Nome muito curto.';
      return '';
    },
    empresa: function (v) {
      return v.trim() ? '' : 'Informe o nome da empresa.';
    },
    email: function (v) {
      if (!v.trim()) return 'Informe seu email.';
      return /^[^\s@]+@[^\s@]+\.[a-z]{2,}$/i.test(v.trim()) ? '' : 'Email inválido.';
    },
    telefone: function (v) {
      var digits = v.replace(/\D/g, '');
      if (!digits) return 'Informe seu telefone.';
      return digits.length >= 10 ? '' : 'Telefone incompleto.';
    },
    segmento: function (v) {
      return v ? '' : 'Selecione um segmento.';
    },
    privacidade: function (_v, el) {
      return el.checked ? '' : 'É preciso aceitar a política de privacidade.';
    }
  };

  function errorSlot(name) {
    return form.querySelector('[data-error-for="' + name + '"]');
  }

  function showError(name, message) {
    var el = form.elements[name];
    var slot = errorSlot(name);

    if (el) el.classList.add('is-invalid');
    if (slot) {
      slot.textContent = message;
      slot.classList.add('is-visible');
    }
  }

  function clearError(name) {
    var el = form.elements[name];
    var slot = errorSlot(name);

    if (el) el.classList.remove('is-invalid');
    if (slot) {
      slot.textContent = '';
      slot.classList.remove('is-visible');
    }
  }

  function validateField(name) {
    var el = form.elements[name];
    if (!el) return true;

    var message = RULES[name](el.value, el);
    if (message) {
      showError(name, message);
      return false;
    }
    clearError(name);
    return true;
  }

  function validateForm() {
    var firstInvalid = null;

    Object.keys(RULES).forEach(function (name) {
      if (!validateField(name) && !firstInvalid) {
        firstInvalid = form.elements[name];
      }
    });

    if (firstInvalid) {
      firstInvalid.focus();
      return false;
    }
    return true;
  }

  /* Limpa o erro assim que o usuário corrige o campo */
  Object.keys(RULES).forEach(function (name) {
    var el = form.elements[name];
    if (!el) return;

    var event = (el.type === 'checkbox' || el.tagName === 'SELECT') ? 'change' : 'input';

    el.addEventListener(event, function () {
      if (el.classList.contains('is-invalid') || errorSlot(name).classList.contains('is-visible')) {
        validateField(name);
      }
    });

    /* No blur só validamos campos de texto já preenchidos, para não
       acusar erro em algo que o usuário apenas tabulou. */
    if (el.type !== 'checkbox') {
      el.addEventListener('blur', function () {
        if (el.value.trim()) validateField(name);
      });
    }
  });

  /* ------------------------------------------------------------------------
     Estados de UI
     ------------------------------------------------------------------------ */
  function setLoading(loading) {
    submitBtn.disabled = loading;
    submitBtn.classList.toggle('is-loading', loading);
  }

  function setFeedback(message, type) {
    feedback.textContent = message;
    feedback.className = 'form__feedback is-visible is-' + type;
  }

  function clearFeedback() {
    feedback.textContent = '';
    feedback.className = 'form__feedback';
  }

  /* ------------------------------------------------------------------------
     Envio
     ------------------------------------------------------------------------ */
  function getPayload() {
    return {
      nameClient: form.elements.nome.value.trim(),
      companyName: form.elements.empresa.value.trim(),
      email: form.elements.email.value.trim().toLowerCase(),
      phone: form.elements.telefone.value.replace(/\D/g, ''),

      /* segment: constante do enum BusinessSegment (definida nos option do HTML). */
      segment: form.elements.segmento.value,
      privacyPolicyAccepted: form.elements.privacidade.checked
    };
  }

  form.addEventListener('submit', function (event) {
    event.preventDefault();
    clearFeedback();

    if (!validateForm()) return;

    var payload = getPayload();
    setLoading(true);

    sendLead(payload)
      .then(function () {
        form.reset();
        Object.keys(RULES).forEach(clearError);
        setFeedback('Recebemos seus dados! Em breve um especialista entra em contato.', 'success');
      })
      .catch(function (err) {
        setFeedback(err.userMessage || 'Não foi possível enviar agora. Tente novamente em instantes.', 'error');
      })
      .then(function () {
        setLoading(false);
      });
  });

  /**
   * POST /lead — envia o lead para a API.
   */
  function sendLead(payload) {
    return fetch(API_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      body: JSON.stringify(payload)
    })
      .catch(function () {
        /* Falha de rede / CORS: o fetch rejeita sem resposta. */
        throw buildError('Servidor indisponível. Verifique sua conexão e tente novamente.');
      })
      .then(function (res) {
        if (res.ok) return parseBody(res);

        return parseBody(res).then(function (body) {
          /* Sem o payload no log: ele carrega email e telefone do lead. */
          console.error('[leadForm] HTTP ' + res.status + ' — ' + JSON.stringify(body));

          var err = buildError(messageFromError(res.status, body));
          err.status = res.status;
          err.body = body;
          throw err;
        });
      });
  }

  /* O back pode responder 201 sem corpo — por isso não usamos res.json() direto. */
  function parseBody(res) {
    return res.text().then(function (text) {
      if (!text) return null;
      try {
        return JSON.parse(text);
      } catch (e) {
        return text;
      }
    });
  }

  function buildError(userMessage) {
    var err = new Error(userMessage);
    err.userMessage = userMessage;
    return err;
  }

  /**
   * Traduz a resposta de erro do Spring numa mensagem para o usuário.
   * Cobre o formato padrão ({ message }) e o de bean validation ({ errors: [...] }).
   */
  function messageFromError(status, body) {
    if (body && typeof body === 'object') {
      if (Array.isArray(body.errors) && body.errors.length) {
        return body.errors
          .map(function (e) { return e.defaultMessage || e.message || e.field; })
          .filter(Boolean)
          .join(' ');
      }
      if (body.message) return body.message;
    }

    if (status === 400 || status === 422) return 'Dados inválidos. Confira os campos e tente novamente.';
    if (status === 409) return 'Este email já está cadastrado.';
    if (status === 429) return 'Muitas tentativas. Aguarde um instante antes de tentar de novo.';
    if (status >= 500) return 'Erro no servidor. Tente novamente em instantes.';

    return 'Não foi possível enviar agora. Tente novamente em instantes.';
  }
})();
